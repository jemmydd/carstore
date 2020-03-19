package com.lym.mechanical.bean.dto.my;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname MyIndexDTO
 * @Description
 * @Date 2019/11/18 9:02
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyIndexDTO {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "是否有名片")
    private Boolean hasCard;

    @ApiModelProperty(value = "名片id")
    private Integer cardId;

    @ApiModelProperty(value = "名片编号")
    private String code;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "到期时间")
    private String vipEndTime;

    @ApiModelProperty(value = "是否车商")
    private Boolean isCarStore;

    @ApiModelProperty(value = "是否VIP")
    private Boolean isVip;

    @ApiModelProperty(value = "今日访客数量")
    private Integer todayGuest;

    @ApiModelProperty(value = "沟通过数量")
    private Integer talkCount;

    @ApiModelProperty(value = "累计访客数量")
    private Integer totalGuest;

    @ApiModelProperty(value = "意向客户数量")
    private Integer intentionCount;

    @ApiModelProperty(value = "手机号")
    private String mobile;
}
