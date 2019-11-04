package com.luoyanjie.mechanical.bean.enumBean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * 上架状态：1正上架、0已下架
 */
public enum PublishShelfStatusEnum {
    IN_THE_SALE((byte) 1, "出售中"),
    OFF((byte) 0, "已下架"),
    ;

    @Getter
    @Setter
    private Byte code;

    @Getter
    @Setter
    private String text;

    PublishShelfStatusEnum(Byte code, String text) {
        this.code = code;
        this.text = text;
    }

    public static PublishShelfStatusEnum byCode(Byte code) {
        PublishShelfStatusEnum publishShelfStatusEnum = null;

        for (PublishShelfStatusEnum row : PublishShelfStatusEnum.values()) {
            if (Objects.equals(code, row.getCode())) {
                publishShelfStatusEnum = row;
            }
        }

        return publishShelfStatusEnum;
    }

    public static Boolean isOff(Byte code) {
        return Objects.equals(byCode(code), OFF);
    }
}
