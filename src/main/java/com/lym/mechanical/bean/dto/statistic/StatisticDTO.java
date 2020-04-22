package com.lym.mechanical.bean.dto.statistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname StatisticDTO
 * @Description
 * @Date 2020/4/22 15:09
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDTO {

    @ApiModelProperty(value = "顶部的数据")
    private StatisticTopDTO top;

    @ApiModelProperty(value = "中间的数据")
    private StatisticMediumDTO medium;

    @ApiModelProperty(value = "底部的数据")
    private StatisticBottom bottom;
}
