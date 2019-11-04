package com.luoyanjie.mechanical.bean.enumBean;

import java.util.Objects;

public enum PublishCallSceneEnum {
    MY_PUBLISH,
    MY_COLLECTION,
    MY_DISCUSS,
    SQUARE,
    ADMIN,
    PERSON_HOME;

    public static Boolean isSquare(PublishCallSceneEnum publishCallSceneEnum) {
        return Objects.equals(publishCallSceneEnum, SQUARE);
    }

    public static Boolean isMy(PublishCallSceneEnum publishCallSceneEnum) {
        return Objects.equals(publishCallSceneEnum, MY_PUBLISH) || Objects.equals(publishCallSceneEnum, MY_COLLECTION) || Objects.equals(publishCallSceneEnum, MY_DISCUSS);
    }
}
