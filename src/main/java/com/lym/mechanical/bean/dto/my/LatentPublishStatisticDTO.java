package com.lym.mechanical.bean.dto.my;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname LatentPublishStatisticDTO
 * @Description
 * @Date 2019/11/18 19:06
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LatentPublishStatisticDTO {

    @ApiModelProperty(value = "发布id")
    private Integer publishId;

    @ApiModelProperty(value = "图片")
    private String image;

    @ApiModelProperty(value = "设备标题")
    private String title;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "发布日期")
    private String date;

    @ApiModelProperty(value = "浏览次数")
    private Integer lookTimes;

    @ApiModelProperty(value = "最长浏览时间")
    private String mostLookTime;

    @ApiModelProperty(value = "最长浏览时间")
    private Long time;

    @ApiModelProperty(value = "是否尝试拨号")
    private String hasTakeMobile;

    @ApiModelProperty(value = "是否收藏")
    private String hasCollect;

    @ApiModelProperty(value = "最近一次访问时间")
    private String recentTime;

    @ApiModelProperty(value = "综合评分")
    private Integer score;
}
