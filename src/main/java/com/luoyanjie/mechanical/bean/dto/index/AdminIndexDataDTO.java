package com.luoyanjie.mechanical.bean.dto.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-15 10:41
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminIndexDataDTO {
    @ApiModelProperty("注册用户数，包含所有的")
    private Integer userCount;

    @ApiModelProperty("发布数，包含所有的")
    private Integer publishCount;
}
