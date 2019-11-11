package com.lym.mechanical.bean.enumBean;

import java.util.Objects;

/**
 * @Classname MessageTypeEnum
 * @Description 消息类型
 * @Date 2019/11/11 9:51
 * @Created by jimy
 * good good code, day day up!
 */
public enum  MessageTypeEnum {
    TEXT("TEXT", "[文本]"),
    IMAGE("IMAGE", "[图片]"),
    VIDEO("VIDEO", "[视频]"),
    PUBLISH("PUBLISH", "[设备]"),
    ;

    private String code;

    private String name;

    MessageTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getNameByCode(String code) {
        for (MessageTypeEnum type : values()) {
            if (Objects.equals(code, type.getCode())) {
                return type.getName();
            }
        }
        return "";
    }
}
