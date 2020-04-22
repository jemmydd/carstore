package com.lym.mechanical.bean.dto.statistic;

import com.lym.mechanical.bean.dto.publish.PublishDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname StatisticBottom
 * @Description
 * @Date 2020/4/22 15:11
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticBottom {

    @ApiModelProperty(value = "本周热门机子")
    private List<PublishDTO> hotPublish;
}
