package com.luoyanjie.mechanical.bean.dto.publish;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-08-05 20:57
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishDetailDTO {
    @ApiModelProperty("发布")
    private PublishDTO publish;

    @ApiModelProperty("留言")
    private List<PublishDiscussDTO> discuss;

    @ApiModelProperty("是否被请求者点赞了")
    private Boolean likeDown;

    @ApiModelProperty("是否被请求者收藏了")
    private Boolean collectionDown;
}
