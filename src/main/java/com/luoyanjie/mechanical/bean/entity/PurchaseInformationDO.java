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
public class PurchaseInformationDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Integer categoryFirstId;

    private Integer categorySecondId;

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String areaCode;

    private String areaName;

    private String newOldLevel;

    private String title;

    private String content;

    private String contactMobile;

    private Integer viewCount;

    private Byte shelfStatus;

    private Integer creator;

    private String expectedPrice;

    private String usageHours;

    private String yearLimitNum;

    private Byte hasInvoice;

    private Byte hasCertificate;

    private String contact;

    private String remark;

    private Integer brandId;
}