package com.lym.mechanical.bean.dto.my;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname MyGuestDTO
 * @Description
 * @Date 2019/11/18 10:29
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyGuestDTO {

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "访客用户id")
    private Integer guestId;

    @ApiModelProperty(value = "访客姓名")
    private String name;

    @ApiModelProperty(value = "最近一次访问时间")
    private String recentTime;
}
