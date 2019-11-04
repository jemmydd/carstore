package com.luoyanjie.mechanical.bean.entitySelf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author luoyanjie
 * @date 2019-07-31 13:51
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishSelfDO {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Byte isDeleted;

    private Integer userId;

    private Integer categoryFirstId;

    private Integer categorySecondId;

    private String phone;

    private String title;

    private String textIntroduce;

    private String voiceIntroduce;

    private Integer voiceIntroduceTime;

    private String mainMedia;

    private String contactPhone;

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String areaCode;

    private String areaName;

    private String inPrice;

    private String outPrice;

    private Integer productiveYear;

    private Integer likeCount;

    private Integer viewCount;

    private Integer discussCount;

    private Integer collectionCount;

    private Integer viewPhoneCount;

    private Byte shelfStatus;
}