package com.lym.mechanical.bean.enumBean;

import lombok.Getter;
import lombok.Setter;

public enum AppEnum {
    SECOND_HAND_MECHANICAL_PG("二手机械小程序");

    @Getter
    @Setter
    private String text;

    AppEnum(String text) {
        this.text = text;
    }
}
