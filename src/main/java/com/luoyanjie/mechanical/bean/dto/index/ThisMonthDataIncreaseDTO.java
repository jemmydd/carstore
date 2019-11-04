package com.luoyanjie.mechanical.bean.dto.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-17 20:15
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThisMonthDataIncreaseDTO {
    @ApiModelProperty("日期")
    private String day;

    @ApiModelProperty("用户增长数")
    private Integer userIncreaseCount;

    @ApiModelProperty("发布增长数")
    private Integer publishIncreaseCount;

    @ApiModelProperty("是否已经过了或者正在这个时间内")
    private Boolean overOrIn;
}
