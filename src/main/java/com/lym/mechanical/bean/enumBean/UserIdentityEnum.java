package com.lym.mechanical.bean.enumBean;

import lombok.Getter;

/**
 * @author luoyanjie
 * @date 2019-08-25 15:17
 * Coding happily every day!
 */
public enum UserIdentityEnum {
    ADMIN("ADMIN"),
    二手机械商("二手机械商"),
    工程机械经销商("工程机械经销商"),
    工程方("工程方"),
    施工方_车队("施工方/车队"),
    工人("工人"),
    非工程从业者("非工程从业者");

    @Getter
    private String text;

    UserIdentityEnum(String text) {
        this.text = text;
    }
}
