package com.lym.mechanical.bean.dto.publish;

import com.lym.mechanical.bean.dto.location.LocationDetailDTO;
import com.lym.mechanical.bean.dto.user.UserDTO;
import com.lym.mechanical.bean.dto.wxPg.OperateDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishDTO {
    @ApiModelProperty("发布ID")
    private Integer id;

    @ApiModelProperty("发布时间")
    private String createTime;

    @ApiModelProperty("1已删除、0未删除")
    private Byte isDeleted;

    @ApiModelProperty("一级类目ID")
    private Integer categoryFirstId;

    @ApiModelProperty("一级类目名称")
    private String categoryFirstName;

    @ApiModelProperty("二级类目ID")
    private Integer categorySecondId;

    @ApiModelProperty("二级类目名称")
    private String categorySecondName;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("文本介绍")
    private String textIntroduce;

    @ApiModelProperty("语音介绍")
    private String voiceIntroduce;

    @ApiModelProperty("语音介绍时长")
    private Integer voiceIntroduceTime;

    @ApiModelProperty("第一个多媒体文件，可能是图片也可能是短视频的首图")
    private String mainMedia;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("位置，冗余字段，就是locationDetail里面的location")
    private String location;

    @ApiModelProperty("入手价、原价")
    private String inPrice;

    @ApiModelProperty("交易价、出手价")
    private String outPrice;

    @ApiModelProperty("年份")
    private Integer productiveYear;

    @ApiModelProperty("点赞量")
    private Integer likeCount;

    @ApiModelProperty("浏览量")
    private Integer viewCount;

    @ApiModelProperty("留言量")
    private Integer discussCount;

    @ApiModelProperty("收藏量")
    private Integer collectionCount;

    @ApiModelProperty("是否已经下架")
    private Boolean isDownShelf;

    //****** START 处理过的数据
    @ApiModelProperty("友好的发布时间")
    private String createTimeFriendly;

    @ApiModelProperty("发布者信息")
    private UserDTO publishUserInfo;

    @ApiModelProperty("省市区信息")
    private LocationDetailDTO locationDetail;

    @ApiModelProperty("所有的多媒体")
    private List<PublishImageVideoDTO> imageVideos;

    @ApiModelProperty("操作按钮，在我的里面会有用到")
    private List<OperateDTO> operates;

    @ApiModelProperty("电话被查看次数")
    private Integer viewPhoneCount;

    //****** START on 191010
    @ApiModelProperty("品牌ID")
    private Integer brandId;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("使用小时数")
    private String usageHours;

    @ApiModelProperty("是否有发票：1有、0无")
    private Byte hasInvoice;

    @ApiModelProperty("是否有合格证：1有、0无")
    private Byte hasCertificate;

    @ApiModelProperty("联系人")
    private String contact;

    @ApiModelProperty("型号")
    private String type;

    @ApiModelProperty("车商用户id")
    private Integer carUserId;

    @ApiModelProperty("车商用户手机号")
    private String carUserMobile;

    @ApiModelProperty("车商名片id")
    private Integer cardId;

    @ApiModelProperty(value = "查看人数")
    private Integer lookCount;
}
