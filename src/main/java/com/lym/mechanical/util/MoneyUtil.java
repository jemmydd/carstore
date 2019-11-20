package com.lym.mechanical.util;

import com.lym.mechanical.bean.common.Constant;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 金额工具类
 * Created by Thor on 2017/5/8.
 * happy coding!
 */
public class MoneyUtil {
    private static final String PATTERN = "\\d+\\.\\d{2}";

    private static final String PATTERN2 = "-?[0-9]+(\\.[0-9]{1,2})?";
    /**
     * 保留两位小数、四舍五入
     *
     * @param money 金额数据
     * @return 保留两位小数后的金额数据
     */
    public static BigDecimal format(BigDecimal money) {
        if (Objects.isNull(money)) {
            return BigDecimal.ZERO;
        }

        return money.setScale(Constant.DECIMAL_DIGITS, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 保留两位小数、四舍五入
     *
     * @param money 金额数据
     * @return 保留两位小数后的金额数据
     */
    public static BigDecimal format(double money) {
        return new BigDecimal(money).setScale(Constant.DECIMAL_DIGITS, BigDecimal.ROUND_HALF_UP);
    }

    public static Double formatReturnDouble(BigDecimal money) {
        return format(money).doubleValue();
    }

    public static Double formatReturnDouble(double money) {
        return format(money).doubleValue();
    }

    public static Integer removeTail(BigDecimal money) {
        if (money == null) {
            return 0;
        }

        return money.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static Boolean checkMoneyFormat(BigDecimal bigDecimal) {
        boolean result = false;

        if (bigDecimal != null) {
            result = Pattern.matches(PATTERN, String.valueOf(bigDecimal));
        }

        return result;
    }

    public static Boolean isValid(BigDecimal bigDecimal) {
        return checkMoneyFormat(bigDecimal);
    }

    public static Boolean isInvalid(BigDecimal bigDecimal) {
        return !isValid(bigDecimal);
    }

    public static Boolean isValid(String bigDecimal) {
        boolean result = false;

        if (bigDecimal != null) {
            result = bigDecimal.matches(PATTERN2);
        }

        return result;
    }

    public static BigDecimal formatAndMovePoint(BigDecimal money, int num) {
        if (Objects.isNull(money)) {
            return new BigDecimal(0);
        }

        BigDecimal data = money.setScale(num, BigDecimal.ROUND_HALF_UP);
        BigDecimal intVal = new BigDecimal(data.intValue());

        return data.compareTo(intVal) == 0 ? intVal : data;
    }

    public static void main(String[] args) {
        System.out.println(isValid("1ee"));
        System.out.println(isValid("1.00"));
        System.out.println(isValid("1.009"));
        System.out.println(isValid("100.9"));
        System.out.println(isValid("100"));
        System.out.println(isValid("0.0"));
        System.out.println(isValid("0.00"));
        System.out.println(isValid("0.000"));
        System.out.println(isValid("00.000"));
        System.out.println(isValid("00.0"));

    }
}
