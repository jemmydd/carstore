package com.lym.mechanical.bean.dto.publish;

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
public class PublishInHpItemDTO {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("多媒体资源")
    private List<String> imageVideos;

    @ApiModelProperty("共几个多媒体资源")
    private Integer imageVideosNum;
}
