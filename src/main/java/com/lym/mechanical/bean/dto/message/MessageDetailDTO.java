package com.lym.mechanical.bean.dto.message;

import com.lym.mechanical.component.result.PageData;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname MessageDetailDTO
 * @Description
 * @Date 2019/11/11 10:09
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDetailDTO {

    @ApiModelProperty(value = "微信号")
    private String wechatNo;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "对方用户id")
    private Integer userId;

    @ApiModelProperty(value = "名片id，没有返回null")
    private Integer cardId;

    @ApiModelProperty(value = "是否已标为意向")
    private Boolean hasIntention;

    @ApiModelProperty(value = "聊天内容")
    private PageData<MessageDetailListDTO> messages;
}
