package com.lym.mechanical.bean.dto.my;

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

    @ApiModelProperty(value = "访客列表头像")
    private List<String> guests;
}
