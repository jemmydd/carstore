package com.lym.mechanical.bean.dto.statistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname StatisticTopDTO
 * @Description
 * @Date 2020/4/22 15:09
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticTopDTO {

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "活跃天数")
    private Integer activeCount;

    @ApiModelProperty(value = "发布设备数")
    private Integer publishCount;

    @ApiModelProperty(value = "累计访客数")
    private Integer totalGuest;
}
