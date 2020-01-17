package com.lym.mechanical.bean.dto.my;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname LookUserDTO
 * @Description
 * @Date 2019/11/18 16:59
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LookUserDTO {

    @ApiModelProperty(value = "潜在用户的用户id")
    private Integer userId;

    @ApiModelProperty(value = "潜在用户的用户头像")
    private String avatar;

    @ApiModelProperty(value = "潜在用户的用户姓名")
    private String name;

    @ApiModelProperty(value = "是否沟通过")
    private Boolean hasTalk;

    @ApiModelProperty(value = "累计浏览次数")
    private Integer totalLookTimes;

    @ApiModelProperty(value = "最长浏览时间")
    private String mostLookTime;

    private Long time;

    @ApiModelProperty(value = "是否尝试拨打电话")
    private String hasTakeMobile;

    @ApiModelProperty(value = "是否收藏")
    private String hasCollect;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "综合评分")
    private Integer score;
}
