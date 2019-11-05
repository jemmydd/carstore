package com.lym.mechanical.bean.enumBean;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public enum ImageVideosTypeEnum {
    IMAGE("图片"),
    VIDEO("视频");

    @Getter
    @Setter
    private String text;

    ImageVideosTypeEnum(String text) {
        this.text = text;
    }

    public static Boolean isVideo(String type) {
        return Objects.equals(type, VIDEO.name());
    }
}
