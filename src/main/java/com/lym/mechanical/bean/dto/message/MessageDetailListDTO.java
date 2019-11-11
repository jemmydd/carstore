package com.lym.mechanical.bean.dto.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname MessageDetailListDTO
 * @Description
 * @Date 2019/11/11 10:12
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDetailListDTO {

    @ApiModelProperty(value = "消息时间戳，毫秒")
    private Long timeStamp;

    @ApiModelProperty(value = "消息内容，文本或图片地址或视频地址")
    private String content;

    @ApiModelProperty(value = "消息类型，TEXT-文本，IMAGE-图片，VIDEO-视频，PUBLISH-设备")
    private String type;

    @ApiModelProperty(value = "设备简要信息，如果type=PUBLISH取该字段")
    private MessagePublishDTO publish;

    @ApiModelProperty(value = "发送人头像")
    private String avatar;

    @ApiModelProperty(value = "是否自己发送的消息")
    private Boolean isSelf;
}
