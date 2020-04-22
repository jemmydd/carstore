package com.lym.mechanical.bean.dto.statistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname StatisticMediumDTO
 * @Description
 * @Date 2020/4/22 15:10
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticMediumDTO {

    @ApiModelProperty(value = "本周趋势图")
    private List<StatisticWeekDTO> weekData;
}
