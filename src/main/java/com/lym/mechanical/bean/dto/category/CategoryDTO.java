package com.lym.mechanical.bean.dto.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-08-03 17:07
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    @ApiModelProperty("类目级别")
    private Integer categoryLevel;

    @ApiModelProperty("类目ID，-1表示'全部'")
    private Integer id;

    @ApiModelProperty("类目名称")
    private String name;

    @ApiModelProperty("类目图标")
    private String icon;

    @ApiModelProperty("子级")
    private List<CategoryDTO> children;
}
