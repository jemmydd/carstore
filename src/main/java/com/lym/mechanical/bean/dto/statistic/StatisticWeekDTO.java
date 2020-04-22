package com.lym.mechanical.bean.dto.statistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname StatisticWeekDTO
 * @Description
 * @Date 2020/4/22 15:13
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticWeekDTO {

    @ApiModelProperty(value = "周几")
    private String weekDay;

    @ApiModelProperty(value = "当日访客数量")
    private Integer guestCount;
}
