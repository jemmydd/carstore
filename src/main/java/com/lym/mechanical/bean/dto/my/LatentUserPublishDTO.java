package com.lym.mechanical.bean.dto.my;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname LatentUserPublishDTO
 * @Description
 * @Date 2019/11/18 16:45
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LatentUserPublishDTO {

    @ApiModelProperty(value = "设备id")
    private Integer publishId;

    @ApiModelProperty(value = "设备主图")
    private String image;

    @ApiModelProperty(value = "设备标题")
    private String title;

    @ApiModelProperty(value = "设备描述")
    private String desc;

    @ApiModelProperty(value = "设备价格")
    private String price;

    @ApiModelProperty(value = "设备发布日期")
    private String date;
}
