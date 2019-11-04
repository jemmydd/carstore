package com.luoyanjie.mechanical.bean.dto.discuss;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-07-31 17:03
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscussDTO {
    @ApiModelProperty("发布ID")
    private Integer publishId;

    @ApiModelProperty("发布标题")
    private String publishTitle;

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("发这个动态的人的ID")
    private Integer sendUserId;

    @ApiModelProperty("头像")
    private String headPortrait;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("时间")
    private String createdTime;

    @ApiModelProperty("主图")
    private String mainMedia;
}
