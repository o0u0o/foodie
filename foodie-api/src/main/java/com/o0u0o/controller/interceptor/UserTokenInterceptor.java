package com.o0u0o.controller.interceptor;

import com.o0u0o.utils.IJsonResult;
import com.o0u0o.utils.JsonUtils;
import com.o0u0o.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 用户令牌拦截器
 * @author o0u0o
 * @date 2021/10/21 3:01 下午
 */
public class UserTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisOperator redisOperator;

    public static final String REDIS_USER_TOKEN = "redis_user_token";

    /**
     * 拦截请求在访问controller之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");

        if (StringUtils.isBlank(userId) && StringUtils.isBlank(userToken)){
            returnErrorResponse(response, IJsonResult.errorMap("请登录..."));
            return false;
        }

        String uniqueToken = redisOperator.get(REDIS_USER_TOKEN + ":" + userId);
        if (StringUtils.isBlank(uniqueToken)){
            returnErrorResponse(response, IJsonResult.errorMap("请登录..."));
            return false;
        }

        //两个token不一致
        if (!uniqueToken.equals(userToken)){
            returnErrorResponse(response, IJsonResult.errorMap("账号可能在异地登录..."));
            return false;
        }


        /**
         * false：请求被拦截，被驳回，验证出现问题
         * true：请求在经过验证校验后，是ok的，是可以放行的
         */
        return true;
    }

    /**
     * 请求访问controller之后，渲染视图之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 请求访问controller之后，渲染视图之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    public void returnErrorResponse(HttpServletResponse response, IJsonResult result){

        OutputStream out = null;

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");
        try {
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
