package com.o0u0o.controller.usercenter;

import com.o0u0o.controller.shop.BaseController;
import com.o0u0o.pojo.Users;
import com.o0u0o.pojo.bo.center.CenterUserBO;
import com.o0u0o.resource.FileUpload;
import com.o0u0o.service.usercenter.UserCenterService;
import com.o0u0o.utils.CookieUtils;
import com.o0u0o.utils.DateUtil;
import com.o0u0o.utils.IJsonResult;
import com.o0u0o.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jodd.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mac
 * @date 2020/12/3 3:25 下午
 */
@Api(value = "用户信息接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserInfoController extends BaseController {

    @Autowired
    private UserCenterService userCenterService;

    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "用户头像修改", notes = "用户头像修改", httpMethod = "GET")
    @PostMapping("/uploadFace")
    public IJsonResult update(@ApiParam(name = "userId", value = "用户ID", required = true)
                              @RequestParam String userId,
                              @ApiParam(name = "file", value = "用户头像", required = true)
                              MultipartFile file,
                              HttpServletRequest request, HttpServletResponse response){

        //防止黑客上传其他文件 如.sh .php 等

        // 定义头像保存地址
//        String fileSpace = IMAGE_USER_FACE_LOCATION;
        String fileSpace = fileUpload.getImageUserFaceLocation();
        // 在路径上为每一个用户增加一个userId 区分不同用户上传
        String uploadPathPrefix = File.separator + userId;

        //开始文件上传
        if (file != null){
            FileOutputStream fileOutputStream = null;
            try {
            //获得文件上传的文件名称
            String fileName = file.getOriginalFilename();
            if (StringUtil.isNotBlank(fileName)){

                //文件重命名 如：laowang-face.png -> ["laowang-face", "png"]
                String[] fileNameArr = fileName.split("\\.");

                //获取文件的后缀名
                String suffix = fileNameArr[fileNameArr.length - 1];

                if (!suffix.equalsIgnoreCase("png") &&
                        !suffix.equalsIgnoreCase("jpg") &&
                        !suffix.equalsIgnoreCase("jpeg")){
                    return IJsonResult.errorMsg("图片格式不正确！");
                }

                //  face-{userid}.png
                // 文件名称重组 覆盖式上传 增量式：需要额外拼接当前时间
                String newFileName = "face-" + userId + "." + suffix;

                // 上传的头像最终保存的位置
                String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;

                //用户提供给web服务访问的图片地址
                uploadPathPrefix += ("/" + newFileName);

                File outFile = new File(finalFacePath);

                if (outFile.getParent()  != null){
                    //创建文件夹
                    outFile.getParentFile().mkdirs();
                }

                //文件输出保存到目录
                fileOutputStream = new FileOutputStream(outFile);

                InputStream inputStream = file.getInputStream();
                IOUtils.copy(inputStream, fileOutputStream);
            }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream !=  null){
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                } catch (IOException e){

                }
            }

        } else {
            return IJsonResult.errorMsg("文件不能为空");
        }
        //获得图片服务地址
        String imageServerUrl = fileUpload.getFileImageServerUrl();

        //由于浏览器可能存在缓存，此处加上时间戳，保证及时更新
        String finalUserFaceUrl = imageServerUrl  + uploadPathPrefix + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);

        //更新用户头像到数据库
        Users userResult = userCenterService.updateUserFace(userId, finalUserFaceUrl);
        // TODO 后续要改，增加令牌Token, 会整合进redis 实现分布式会话管理

        return IJsonResult.ok();

    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "GET")
    @PostMapping("/update")
    public IJsonResult update(@ApiParam(name = "userId", value = "用户ID", required = true)
                              @RequestParam String userId,
                              @RequestBody @Valid CenterUserBO centerUserBO,
                              BindingResult result,
                              HttpServletRequest request,
                              HttpServletResponse response){
        //判断 BindingResult 是否保存错误的验证信息，如果有，则直接return;
        if (result.hasErrors()){
            Map<String, String> errors = getErrors(result);
            return IJsonResult.errorMap(errors);
        }
        Users userResult = userCenterService.updateUserInfo(userId, centerUserBO);
        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        // TODO 后续要改，增加令牌Token, 会整合进redis 实现分布式会话管理
        return IJsonResult.ok();

    }




    //=========== PRIVATE ===========
    private Map<String, String> getErrors(BindingResult result){
        List<FieldError> errorList = result.getFieldErrors();
        HashMap<String, String> map = new HashMap<>();
        for (FieldError error : errorList) {

            //发送验证错误所对应的某一个属性
            String errorField = error.getField();
            //验证错误的信息
            String errorMsg = error.getDefaultMessage();
            map.put("errorField", errorField);
            map.put("errorMsg", errorMsg);

        }
        return map;
    }

    /**
     * 敏感信息置空
     * @param userResult
     * @return
     */
    private Users setNullProperty(Users userResult){
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

}
