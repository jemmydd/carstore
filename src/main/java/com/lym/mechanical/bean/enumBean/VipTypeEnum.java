package com.lym.mechanical.bean.enumBean;

import com.lym.mechanical.bean.common.Constant;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Classname VipTypeEnum
 * @Description
 * @Date 2020/1/14 17:47
 * @Created by jimy
 * good good code, day day up!
 */
public enum VipTypeEnum {
    BYTE_1((byte) 1, "3天试用", new BigDecimal("1")),
    BYTE_2((byte) 2, "一年VIP", new BigDecimal("298")),
    ;

    private Byte code;

    private String name;

    private BigDecimal amount;

    VipTypeEnum(Byte code, String name, BigDecimal amount) {
        this.code = code;
        this.name = name;
        this.amount = amount;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public static VipTypeEnum getType(Byte code) {
        for (VipTypeEnum type : values()) {
            if (Objects.equals(code, type.getCode())) {
                return type;
            }
        }
        return null;
    }
}
