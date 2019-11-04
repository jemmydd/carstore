package com.luoyanjie.mechanical.bean.enumBean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author luoyanjie
 * @date 2019-07-31 16:18
 * Coding happily every day!
 */
public enum OperateTypeEnum {
    PUBLISH_OFF("下架"),
    PUBLISH_MODIFY("修改"),
    PUBLISH_RE_ON("重新发布"),
    PUBLISH_REMOVE_COLLECTION("取消收藏"),

    PI_OFF("下架"),
    PI_MODIFY("修改"),
    PI_ON("重新上架"),
    ;

    @Getter
    @Setter
    private String text;

    OperateTypeEnum(String text) {
        this.text = text;
    }
}
