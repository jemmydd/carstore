package com.luoyanjie.mechanical.bean.entitySelf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author luoyanjie
 * @date 2019-08-24 21:16
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseInformationSelfDO {
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

    //20190925添加的
    private String expectedPrice;

    private String usageHours;

    private String yearLimitNum;

    private String otherDemand;

    private String contact;

    private String remark;
}
