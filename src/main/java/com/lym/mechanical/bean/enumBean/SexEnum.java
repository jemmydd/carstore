package com.lym.mechanical.bean.enumBean;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public enum SexEnum {
    MALE("M", "女"),
    FEMALE("F", "男");

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String text;

    SexEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static String getTextByCode(String code) {
        String r = "";
        for (SexEnum sexEnum : SexEnum.values()) {
            if (Objects.equals(code, sexEnum.code)) {
                r = sexEnum.getText();
                break;
            }

        }
        return r;
    }
}
