package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-21 16:01
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminCarUserDTO {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "联系电话")
    private String mobile;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "职务")
    private String jobTitle;

    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @ApiModelProperty(value = "是否会员")
    private String isVip;

    @ApiModelProperty(value = "会员到期时间")
    private String vipEndTime;

    @ApiModelProperty(value = "会员购买时间")
    private String buyTime;

    @ApiModelProperty(value = "今日访客")
    private Integer todayGuest;

    @ApiModelProperty(value = "累计访客")
    private Integer totalGuest;

    @ApiModelProperty(value = "沟通过")
    private Integer talkGuest;

    @ApiModelProperty(value = "意向访客")
    private Integer intentionGuest;

    @ApiModelProperty(value = "设备数")
    private Integer publishCount;

    @ApiModelProperty(value = "友情合作")
    private Integer friendCount;

    @ApiModelProperty(value = "名片编号")
    private String code;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "是否同步二手平台")
    private Boolean isShow;
}
