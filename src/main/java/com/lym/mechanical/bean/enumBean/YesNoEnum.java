package com.lym.mechanical.bean.enumBean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author luoyanjie
 * @date 2019-09-30 22:07
 * Coding happily every day!
 */
public enum YesNoEnum {
    YES((byte) 1),
    NO((byte) 0);

    @Getter
    @Setter
    private Byte code;

    YesNoEnum(Byte code) {
        this.code = code;
    }
}
