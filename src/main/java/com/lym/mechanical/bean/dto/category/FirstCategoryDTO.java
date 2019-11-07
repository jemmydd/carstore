package com.lym.mechanical.bean.dto.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FirstCategoryDTO {
    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("icon")
    private String icon;

    @ApiModelProperty("是否展示在首页")
    private Boolean IsShowIndex;

    @ApiModelProperty("二级分类名称列表")
    private List<String> secondName;

    @ApiModelProperty("二级分类名称顿号隔开")
    private String secondNameStr;

    @ApiModelProperty("二级分类列表")
    private List<SecondCategoryDTO> secondCategories;
}
