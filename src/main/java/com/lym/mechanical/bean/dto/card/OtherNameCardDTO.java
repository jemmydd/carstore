package com.lym.mechanical.bean.dto.card;

import com.lym.mechanical.bean.dto.publish.PublishDTO;
import com.lym.mechanical.bean.dto.publish.PublishImageVideoDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname OtherNameCardDTO
 * @Description
 * @Date 2019/11/6 17:35
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtherNameCardDTO {

    @ApiModelProperty(value = "名片信息")
    private NameCardDTO nameCard;

    @ApiModelProperty(value = "个人介绍-文字")
    private String introduce;

    @ApiModelProperty(value = "个人介绍语音地址")
    private String voiceIntroduce;

    @ApiModelProperty(value = "个人介绍语音时间")
    private Integer voiceIntroduceTime;

    @ApiModelProperty(value = "最新的一条设备")
    private PublishDTO publish;

    @ApiModelProperty(value = "风采展示-全部")
    private List<PublishImageVideoDTO> imageVideos;

    @ApiModelProperty(value = "友情合作")
    private List<FriendUsers> friendUsers;
}
