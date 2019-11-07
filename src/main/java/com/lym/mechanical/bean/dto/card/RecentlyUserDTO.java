package com.lym.mechanical.bean.dto.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname RecentlyUserDTO
 * @Description
 * @Date 2019/11/6 17:47
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecentlyUserDTO {

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "用户id")
    private Integer userId;
}
