package com.lym.mechanical.bean.dto.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
