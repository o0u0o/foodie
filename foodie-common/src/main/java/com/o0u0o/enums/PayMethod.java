package com.o0u0o.enums;

/**
 * 支付方式枚举
 * @author mac
 * @date 2020/11/30 1:28 下午
 */
public enum PayMethod {

    WEIXIN(1, "微信"),

    ALIPAY(2, "支付宝");

    public final Integer type;
    public final String value;

    PayMethod(Integer type, String value){
        this.type = type;
        this.value = value;
    }

}
