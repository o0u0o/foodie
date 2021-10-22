package com.o0u0o.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 自定义响应数据结构
 * 				本类可提供给 H5/ios/安卓/公众号/小程序 使用
 * 				前端接受此类数据（json object)后，可自行根据业务去实现相关功能
 *
 * 				200：表示成功
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回
 * 				502：拦截器拦截到用户token出错
 * 				555：异常抛出信息
 * 				556: 用户qq校验异常
 * 			    557: 校验用户是否在cas登录（用户门票的校验）
 * @author o0u0o
 * @date 2020/10/26 2:39 下午
 */
@ApiModel(value = "响应结果")
public class IJsonResult {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @ApiModelProperty(value="响应状态")
    private Integer status;


    @ApiModelProperty(value="响应消息")
    private String msg;

    @ApiModelProperty(value="响应数据")
    private Object data;

    @JsonIgnore
    private String ok;	// 不使用

    public static IJsonResult build(Integer status, String msg, Object data) {
        return new IJsonResult(status, msg, data);
    }

    public static IJsonResult build(Integer status, String msg, Object data, String ok) {
        return new IJsonResult(status, msg, data, ok);
    }

    public static IJsonResult ok(Object data) {
        return new IJsonResult(data);
    }

    public static IJsonResult ok() {
        return new IJsonResult(null);
    }

    public static IJsonResult errorMsg(String msg) {
        return new IJsonResult(500, msg, null);
    }

    public static IJsonResult errorMap(Object data) {
        return new IJsonResult(501, "error", data);
    }

    public static IJsonResult errorTokenMsg(String msg) {
        return new IJsonResult(502, msg, null);
    }

    public static IJsonResult errorException(String msg) {
        return new IJsonResult(555, msg, null);
    }

    public static IJsonResult errorUserQQ(String msg) {
        return new IJsonResult(556, msg, null);
    }

    /** 错误的用户门票 */
    public static IJsonResult errorUserTicket(String msg) {
        return new IJsonResult(557, msg, null);
    }

    public IJsonResult() {

    }

    public IJsonResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public IJsonResult(Integer status, String msg, Object data, String ok) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.ok = ok;
    }

    public IJsonResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}
