package com.luoyanjie.mechanical.bean.dto.banner;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-12 22:02
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecondCategoryDTO {
    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("icon")
    private String icon;
}
