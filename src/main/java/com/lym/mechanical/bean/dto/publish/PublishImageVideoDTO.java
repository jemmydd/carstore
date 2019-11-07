package com.lym.mechanical.bean.dto.publish;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishImageVideoDTO {
    @ApiModelProperty("地址")
    private String file;

    @ApiModelProperty("类型,IMAGE-图片，VIDEO-视频")
    private String type;
}
