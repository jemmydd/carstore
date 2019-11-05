package com.lym.mechanical.bean.enumBean;

import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author luoyanjie
 * @date 2019-07-29 17:56
 * Coding happily every day!
 */
public enum EnvEnum {
    PROD,
    TEST,
    LOCAL,
    ;

    public static Boolean isProd(String env) {
        return (!StringUtils.isEmpty(env)) && Objects.equals(env.toUpperCase(), EnvEnum.PROD.name());
    }
}
