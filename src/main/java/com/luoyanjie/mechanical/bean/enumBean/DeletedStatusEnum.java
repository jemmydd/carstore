package com.luoyanjie.mechanical.bean.enumBean;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public enum DeletedStatusEnum {
    DELETED((byte) 1),
    EXIST((byte) 0);

    @Getter
    @Setter
    private Byte code;

    DeletedStatusEnum(Byte code) {
        this.code = code;
    }

    public static Boolean isDeleted(Byte code) {
        return Objects.equals(code, DELETED.code);
    }
}
