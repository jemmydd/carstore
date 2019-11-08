package com.lym.mechanical.bean.dto.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname MessageDTO
 * @Description
 * @Date 2019/11/8 16:52
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    @ApiModelProperty(value = "对方的用户id")
    private Integer userId;

    @ApiModelProperty(value = "对方的用户头像")
    private String avatar;

    @ApiModelProperty(value = "对方的用户昵称")
    private String name;

    @ApiModelProperty(value = "最近的一条信息")
    private String message;

    @ApiModelProperty(value = "时间")
    private String time;
}
