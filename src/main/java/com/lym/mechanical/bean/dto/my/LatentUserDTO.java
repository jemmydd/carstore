package com.lym.mechanical.bean.dto.my;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname LatentUserDTO
 * @Description
 * @Date 2019/11/18 19:03
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LatentUserDTO {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "是否沟通过")
    private Boolean hasTalk;

    @ApiModelProperty(value = "浏览设备次数")
    private String lookTimes;

    @ApiModelProperty(value = "浏览设备数量")
    private String lookCount;

    @ApiModelProperty(value = "收藏设备数量")
    private String collectCount;

    @ApiModelProperty(value = "手机号")
    private String mobile;
}
