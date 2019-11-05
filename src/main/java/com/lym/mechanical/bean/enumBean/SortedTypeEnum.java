package com.lym.mechanical.bean.enumBean;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum SortedTypeEnum {
    NEWEST,
    PRICE_HIGH,
    PRICE_LOW,
    ;

    public static Boolean contain(String st) {
        return !StringUtils.isEmpty(st)
                && Arrays.stream(SortedTypeEnum.values()).map(SortedTypeEnum::name).collect(Collectors.toList()).contains(st);
    }
}
