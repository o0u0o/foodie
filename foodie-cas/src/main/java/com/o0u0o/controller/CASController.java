package com.o0u0o.controller;

import com.o0u0o.consts.RedisConst;
import com.o0u0o.pojo.Users;
import com.o0u0o.pojo.vo.UsersVO;
import com.o0u0o.service.shop.UserService;
import com.o0u0o.utils.IJsonResult;
import com.o0u0o.utils.JsonUtils;
import com.o0u0o.utils.MD5Utils;
import com.o0u0o.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 统一认证管理系统Controller
 * @Author o0u0o
 * @Date 2020/5/12 10:56 下午
 * @Descripton: 统一认证管理系统Controller
 **/
@Controller
public class CASController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisOperator redisOperator;


    @GetMapping("/login")
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        Model model,
                        String returnUrl){
        model.addAttribute("returnUrl", returnUrl);

        //1、获取userTicket门票，如果cookie中能够获取到，证明用户登录过，此时签发一个一次性的临时票据，并且回跳
        String userTicket = getCookie(request, RedisConst.COOKIE_USER_TICKET);

        boolean isVerified = verifyUserTicket(userTicket);
        if (isVerified){
            String tmpTicket = createTmpTicket();
            return "redirect:" + returnUrl + "?tmpTicket=" + tmpTicket;
        }

        // 2、用户从未登录过，第一次进入则跳转到cas的统一登录页面
        return "login";
    }

    /**
     * CAS的统一登录接口
     * 目的：
     *  1、登录后创建用户会话 -> uniqueToken
     *  2、创建用户全局门票,用于表示在cas端是否登录 -> userTicket
     *  3、创建用户的临时票据，用于回跳，回传 -> tmpTicket
     * @param request
     * @param response
     * @param model
     * @param username
     * @param password
     * @param returnUrl
     * @return
     * @throws Exception
     */
    @PostMapping("/doLogin")
    public String doLogin(HttpServletRequest request,
                          HttpServletResponse response,
                          Model model,
                          String username,
                          String password,
                          String returnUrl) throws Exception {
        model.addAttribute("returnUrl", returnUrl);

        // 0、校验用户名和密码必须不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            model.addAttribute("errorMsg", "用户名或密码不能为空");
            return "login";
        }

        // 1、实现登录业务
        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (userResult ==  null){
            model.addAttribute("errorMsg", "用户名和密码不正确");
            return "login";
        }

        // 2、实现用户的redis会话 生成用户token 存入redis会话
        conventUsersVO(userResult);

        // 3、生成ticket门票，全局门票，代表用户在CAS端登录过
        String userTicket = UUID.randomUUID().toString().trim();
        // 3.1 用户全局门票需要放入cas端的cookie中
        setCookie(RedisConst.COOKIE_USER_TICKET, userTicket, response);

        // 4、userTicket需要关联用户id，并且放入到redis中，代表用户有了门票，可以在各个系统进行访问
        redisOperator.set(RedisConst.REDIS_USER_TICKET + ":" + userTicket, userResult.getId());

        // 5、生成临时票据，回跳到调用端网站（是由CAS端所签发的一个一次性的临时ticket，类型于微信的code码）
        String tmpTicket = createTmpTicket();

        //6、回跳
        /**
         * userTicket：用于表示用户在CAS端登录状态：已经登录
         * tmpTicket：用于颁发给用户进行一次性的验证的票据，有时效性
         */

        /**
         * 举例：
         * 我们去动物园玩耍，大门口买了一张通票，这个就cas系统的全局门票和用户全局会话
         * 动物园里有一些小的景点，需要凭你的门票去领取一次性的票据，有了这些票据以后，就可以去这些小的景点
         * 这样一个小的景点就是我们这里对应一个个的站点，
         * 当我们使用完毕这个票据后，就需要销毁。
         */
        return "redirect:" + returnUrl + "?tmpTicket=" + tmpTicket;
    }

    /**
     * 验证零售票据
     * @param request
     * @param response
     * @param tmpTicket
     * @return
     * @throws Exception
     */
    @PostMapping("/verifyTmpTicket")
    @ResponseBody
    public IJsonResult verifyTmpTicket(HttpServletRequest request,
                                       HttpServletResponse response,
                                       String tmpTicket) throws Exception {
        // 使用一次性临时票据来验证用户是否登录，如果登录过，把用户会话信息返回给站点
        // 使用完毕后，需要销毁临时票据
        String tmpTicketValue = redisOperator.get(RedisConst.REDIS_TMP_TICKET + ":" + tmpTicket);
        if (StringUtils.isBlank(tmpTicketValue)){
            System.out.println("使用完毕后，需要销毁临时票据");
            return IJsonResult.errorUserTicket("用户票据异常");
        }

        // 0、如果这个临时票据OK，则需要销毁，并且拿到cas端cookie的全局userTicket，以此再获取用户会话
        if (!tmpTicketValue.equals(MD5Utils.getMD5Str(tmpTicket))) {
            System.out.println("如果这个临时票据OK，则需要销毁，");
            return IJsonResult.errorUserTicket("用户票据异常");
        } else {
            // 1、销毁临时票据
            redisOperator.del(RedisConst.REDIS_TMP_TICKET + ":" + tmpTicket);
        }
        
        //1.验证并且换取用户userTicket
        String userTicket = getCookie(request, RedisConst.COOKIE_USER_TICKET);
        System.out.println("======userTicket：" + userTicket);
        String userId = redisOperator.get(RedisConst.REDIS_USER_TICKET + ":" + userTicket);
        if (StringUtils.isBlank(userId)){
            System.out.println("验证并且换取用户userTicket");
            return IJsonResult.errorUserTicket("用户票据异常");
        }

        //2.验证门票对应的user会话是否存在
        String userRedis = redisOperator.get(RedisConst.REDIS_USER_TOKEN + ":" + userId);
        if (StringUtils.isBlank(userRedis)){
            System.out.println("验证门票对应的user会话是否存在");
            return IJsonResult.errorUserTicket("用户票据异常");
        }

        // 3.验证成功，返回ok 携带redis查询出的用户会话
        return IJsonResult.ok(JsonUtils.jsonToPojo(userRedis, UsersVO.class));
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @param userId
     * @return
     */
    @PostMapping("/logout")
    @ResponseBody
    public IJsonResult logout(HttpServletRequest request,
                              HttpServletResponse response,
                              String userId){
        // 0、获取cas中的用户门票
        String userTicket = getCookie(request, RedisConst.COOKIE_USER_TICKET);

        // 1、清除userTicket票据 redis/cookie
        deleteCookie(RedisConst.COOKIE_USER_TICKET, response);
        redisOperator.del(RedisConst.COOKIE_USER_TICKET + ":" + userTicket);

        // 2、清除用户的全局会话（分布式会话）
        redisOperator.del(RedisConst.REDIS_USER_TOKEN + ":"  + userId);

        return IJsonResult.ok();
    }


    /**
     * 校验CAS全局用户门票
     * @param userTicket
     * @return
     */
    private boolean verifyUserTicket(String userTicket){

        // 0.验证CAS门票不能为空
        if (StringUtils.isBlank(userTicket)){
            return false;
        }

        // 1.验证CAS门票是否有效
        String userId = redisOperator.get(RedisConst.REDIS_USER_TICKET + ":" + userTicket);
        if (StringUtils.isBlank(userId)){
            return false;
        }

        // 2. 验证门票对应的user会话是否存在
        String userRedis = redisOperator.get(RedisConst.REDIS_USER_TOKEN + ":" + userId);
        if (StringUtils.isBlank(userRedis)){
            return false;
        }
        
        return true;
    }

    /**
     * 获取cookie
     * @param request
     * @param key
     * @return
     */
    private String getCookie(HttpServletRequest request, String key){
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || StringUtils.isBlank(key)){
            System.out.println("====key:" + key);
            System.out.println("===cookieList:" + cookieList);
            return null;
        }

        String cookieValue = null;
        for (int i = 0; i < cookieList.length; i++){
            if (cookieList[i].getName().equals(key)){
                cookieValue = cookieList[i].getValue();
                break;
            }
        }
        return cookieValue;
    }

    /**
     * 设置cookie
     */
    private void setCookie(String key,
                           String val,
                           HttpServletResponse response){
        Cookie cookie = new Cookie(key, val);
        cookie.setDomain("sso.com");
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private void deleteCookie(String key,
                              HttpServletResponse response){
        Cookie cookie = new Cookie(key, null);
        cookie.setDomain("sso.com");
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

    /**
     * 创建临时票据，并保存到redis中
     * @return
     */
    private String createTmpTicket() {
        String tmpTicket = UUID.randomUUID().toString().trim();
        //存储到临时票据到redis 过期时间600秒
        try {
            redisOperator.set(RedisConst.REDIS_TMP_TICKET + ":" + tmpTicket, MD5Utils.getMD5Str(tmpTicket), 600);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tmpTicket;
    }

    /**
     * 实现用户的redis会话 生成用户token 存入redis会话
     * @param userResult
     * @return
     */
    private void conventUsersVO(Users userResult){
        //实现用户的redis会话 生成用户token 存入redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userResult, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        redisOperator.set(RedisConst.REDIS_USER_TOKEN + ":" + userResult.getId(), JsonUtils.objectToJson(usersVO));
    }

}
