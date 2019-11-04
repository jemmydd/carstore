package com.luoyanjie.mechanical.bean.param.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-14 19:26
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifySecondCategoryParam {
    @ApiModelProperty("二级分类ID，新增的时候没有")
    private Integer secondId;

    @ApiModelProperty("icon")
    private String icon;

    @ApiModelProperty("分类名，不能重复")
    private String name;
}
