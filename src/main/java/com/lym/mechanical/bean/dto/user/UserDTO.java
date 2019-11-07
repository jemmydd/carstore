package com.lym.mechanical.bean.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @ApiModelProperty("用户已经被删除/拉黑")
    private Byte isDeleted;

    @ApiModelProperty("用户ID")
    private Integer id;

    @ApiModelProperty("注册时间")
    private String createTime;

    @ApiModelProperty("背景")
    private String backgroundImage;

    @ApiModelProperty("头像")
    private String headPortrait;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("用户身份")
    private String userIdentity;

    @ApiModelProperty("省份编码")
    private String provinceCode;

    @ApiModelProperty("省份名称")
    private String provinceName;

    @ApiModelProperty("城市编码")
    private String cityCode;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("区编码")
    private String areaCode;

    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty("位置")
    private String location;

    @ApiModelProperty("发布数")
    private Integer publishCount;

    @ApiModelProperty("查看手机数")
    private Integer viewPhoneCount;

    @ApiModelProperty("粉丝数")
    private Integer fansCount;

    @ApiModelProperty("关注数")
    private Integer followCount;

    @ApiModelProperty("收藏数")
    private Integer collectionCount;

    @ApiModelProperty("发布的求购信息数量")
    private Integer purchaseInformationCount;

    @ApiModelProperty("邀请数量")
    private Integer inviteCount;

    @ApiModelProperty("签名")
    private String signature;

    @ApiModelProperty("我的未读动态数，这个是从其他表获取的值")
    private Integer unreadCommentCount;

    @ApiModelProperty("是否是新绑定的用户")
    private Boolean newBind;
}
