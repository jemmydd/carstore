package com.luoyanjie.mechanical.bean.param.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-08-14 18:59
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyFirstCategoryParam {
    @ApiModelProperty("一级分类ID")
    private Integer firstId;

    @ApiModelProperty("icon")
    private String icon;

    @ApiModelProperty("分类名，不能重复")
    private String name;

    @ApiModelProperty("是否展示在首页：true是的、false不是")
    private Boolean isShowIndex;

    @ApiModelProperty("排序码")
    private Integer sortedNum;

    @ApiModelProperty("二级分类")
    private List<ModifySecondCategoryParam> secondCategories;
}
