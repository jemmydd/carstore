package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-19 16:16
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminPublishDTO {

    @ApiModelProperty(value = "设备id")
    private Integer publishId;

    @ApiModelProperty(value = "发布者信息")
    private CarUserDTO user;

    @ApiModelProperty(value = "主图")
    private String image;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "型号")
    private String type;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "出厂年份")
    private String productiveYear;

    @ApiModelProperty(value = "使用时长")
    private String usageHours;

    @ApiModelProperty(value = "设备位置")
    private String location;

    @ApiModelProperty(value = "车主描述")
    private String introduce;

    @ApiModelProperty(value = "合格证")
    private String hasCertificate;

    @ApiModelProperty(value = "发票")
    private String hasInvoice;

    @ApiModelProperty(value = "收藏人数")
    private Integer collectCount;

    @ApiModelProperty(value = "咨询人数")
    private Integer askCount;

    @ApiModelProperty(value = "浏览次数")
    private String lookTimes;

    @ApiModelProperty(value = "浏览人数")
    private String lookCount;

    @ApiModelProperty(value = "发布时间")
    private String createTime;
}
