package com.o0u0o.utils;

import java.math.BigDecimal;

/**
 * 货币utils
 * @author mac
 * @date 2020/12/1 9:16 上午
 */
public class CurrencyUtils {

    /**
     *
     * @Description: 分转元，带有小数点
     * @param amount
     */
    public static String getFen2YuanWithPoint(Integer amount) {
        return BigDecimal.valueOf(amount).divide(new BigDecimal(100)).toString();
    }

    /**
     *
     * @Description: 分转元，不带小数，只取整数位
     * @param amount
     */
    public static Integer getFen2Yuan(Integer amount) {
        return BigDecimal.valueOf(amount).divide(new BigDecimal(100)).intValue();
    }

    /**
     *
     * @Description: 元转分，返回整数
     * @param amount
     */
    public static Integer getYuan2Fen(Integer amount) {
        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).intValue();
    }

    /**
     *
     * @Description: 元转分，返回整数
     * @param amount
     * @return
     */
    public static Integer getYuan2Fen(String amount) {
        return BigDecimal.valueOf(Double.valueOf(amount)).multiply(new BigDecimal(100)).intValue();
    }

//	public static void main(String[] args) {
//		System.out.println(new CurrencyUtils().getYuan2Fen("12.366"));
//	}
}
