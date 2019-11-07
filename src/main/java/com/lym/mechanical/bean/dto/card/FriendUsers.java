package com.lym.mechanical.bean.dto.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname FriendUsers
 * @Description
 * @Date 2019/11/7 11:10
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendUsers {

    @ApiModelProperty(value = "名片id")
    private Integer cardId;

    @ApiModelProperty(value = "名片所属用户id")
    private Integer userId;

    @ApiModelProperty(value = "名片姓名")
    private String name;

    @ApiModelProperty(value = "头像")
    private String avatar;
}
