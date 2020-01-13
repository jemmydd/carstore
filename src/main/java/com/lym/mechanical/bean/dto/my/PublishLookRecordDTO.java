package com.lym.mechanical.bean.dto.my;

import com.lym.mechanical.bean.dto.location.LocationDetailDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname PublishLookRecordDTO
 * @Description
 * @Date 2019/11/18 14:30
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublishLookRecordDTO {

    @ApiModelProperty(value = "设备id")
    private Integer publishId;

    @ApiModelProperty(value = "设备标题")
    private String title;

    @ApiModelProperty(value = "设备主图")
    private String image;

    @ApiModelProperty(value = "浏览人数")
    private Long lookCount;

    @ApiModelProperty(value = "收藏人数")
    private Long collectCount;

    @ApiModelProperty("年份")
    private Integer productiveYear;

    @ApiModelProperty("使用小时数")
    private String usageHours;

    @ApiModelProperty("省市区信息")
    private LocationDetailDTO locationDetail;

    @ApiModelProperty("位置，冗余字段，就是locationDetail里面的location")
    private String location;

    @ApiModelProperty("发布时间")
    private String createTime;

    @ApiModelProperty("入手价、原价")
    private String inPrice;

    @ApiModelProperty("交易价、出手价")
    private String outPrice;

    @ApiModelProperty(value = "访客列表头像")
    private List<String> guests;
}
