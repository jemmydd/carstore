package com.lym.mechanical.bean.param.card;

import com.lym.mechanical.bean.dto.publish.PublishImageVideoDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname NameCardParam
 * @Description
 * @Date 2019/11/7 14:43
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NameCardParam {

    @ApiModelProperty(value = "名片id，新增时不必传")
    private Integer cardId;

    @ApiModelProperty(value = "当前登录用户id")
    private Integer userId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "联系电话")
    private String mobile;

    @ApiModelProperty(value = "微信号")
    private String wechatNo;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "职位")
    private String jobTitle;

    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @ApiModelProperty(value = "公司位置坐标")
    private String companyLocation;

    @ApiModelProperty(value = "介绍")
    private String introduce;

    @ApiModelProperty(value = "语音介绍文件地址")
    private String introduceVoice;

    @ApiModelProperty(value = "语音介绍时间长度")
    private Integer introduceVoiceTime;

    @ApiModelProperty(value = "风采展示")
    private List<PublishImageVideoDTO> files;

    @ApiModelProperty(value = "友情合作名片id列表")
    private List<Integer> friendCardIds;
}
