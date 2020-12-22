package com.o0u0o.exception;

import com.o0u0o.utils.IJsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 自定义异常
 * @author mac
 * @date 2020/12/22 5:44 下午
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * 上传文件超过2MB捕获异常 建议文件上传大小不超过500kb
     * @param exception
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public IJsonResult handlerMaxUploadFile(MaxUploadSizeExceededException exception){
        return IJsonResult.errorMsg("文件上传大小不能超过2MB,请压缩图片，或者降低图片质量上传");
    }
}
