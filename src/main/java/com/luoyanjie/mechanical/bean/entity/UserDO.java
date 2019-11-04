package com.luoyanjie.mechanical.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Byte isDeleted;

    private String backgroundImage;

    private String headPortrait;

    private String name;

    private String nickName;

    private String sex;

    private String phone;

    private String userIdentity;

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String areaCode;

    private String areaName;

    private Integer publishCount;

    private Integer viewPhoneCount;

    private Integer fansCount;

    private Integer followCount;

    private Integer collectionCount;

    private Integer purchaseInformationCount;

    private Integer inviteCount;

    private String signature;

    private String account;

    private String password;

    private String token;

    private String openid;

    private Byte newBind;
}