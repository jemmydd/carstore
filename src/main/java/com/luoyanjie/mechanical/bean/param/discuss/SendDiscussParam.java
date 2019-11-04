package com.luoyanjie.mechanical.bean.param.discuss;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-07 20:19
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendDiscussParam {
    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("发布ID")
    private Integer publishId;

    @ApiModelProperty("回复的动态的ID")
    private Integer beDiscussId;

    @ApiModelProperty("主楼ID")
    private Integer floorDiscussId;

    @ApiModelProperty("主楼发布者ID")
    private Integer floorDiscussUserId;

    @ApiModelProperty("内容")
    private String content;
}
