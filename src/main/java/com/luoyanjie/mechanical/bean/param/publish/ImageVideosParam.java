package com.luoyanjie.mechanical.bean.param.publish;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-03 21:09
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageVideosParam {
    @ApiModelProperty("类型：IMAGE-图片、VIDEO-视频")
    private String type;

    @ApiModelProperty("文件地址")
    private String fileUrl;

    /*@ApiModelProperty("文件地址")
    private String firstImgUrl;*/
}
