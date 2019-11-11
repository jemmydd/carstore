package com.lym.mechanical.bean.param.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname MessageSendParam
 * @Description
 * @Date 2019/11/11 10:50
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendParam {

    @ApiModelProperty(value = "当前用户id")
    private Integer userId;

    @ApiModelProperty(value = "对方用户id")
    private Integer otherUserId;

    @ApiModelProperty(value = "发送内容，TEXT传文本内容，图片和视频传文件地址，设备传设备id")
    private String content;

    @ApiModelProperty(value = "消息类型，TEXT-文本，IMAGE-图片，VIDEO-视频，PUBLISH-设备")
    private String type;
}
