package com.lym.mechanical.bean.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname PublishLookDTO
 * @Description
 * @Date 2020/4/22 17:24
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublishLookDTO {

    private Integer publishId;

    private Integer count;
}
