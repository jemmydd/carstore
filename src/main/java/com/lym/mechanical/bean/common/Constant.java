package com.lym.mechanical.bean.common;

/**
 * @author liyimin
 * @date 2019-11-05 09:06:21
 * good good code, day day up!
 */
public interface Constant {
    //****** START 分隔符
    String SPLIT = ";";

    //****** START check
    Integer MAX_PHONE_LENGTH = 11;//最长手机号

    //****** publish param
    String PP_APP = "app";
    String PP_CLIENT = "client";
    String PP_VERSION = "version";
    String PP_TOKEN = "token";
    String PP_USER_ID = "userId";

    //****** date
    String INT_DATE_FORMAT = "yyyyMMdd";
    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //****** admin user
    Integer ADMIN_USER_ID = 1;
    String DEFAULT_ADMIN_USER = "account";
    String DEFAULT_ADMIN_PWD = "admin.laogao";
    String DEFAULT_ADMIN_TOKEN = "dbad26f4daa9a67ccba01baf7d28bbb7";
}
