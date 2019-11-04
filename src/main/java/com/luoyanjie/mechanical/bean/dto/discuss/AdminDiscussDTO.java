package com.luoyanjie.mechanical.bean.dto.discuss;

import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-14 23:11
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDiscussDTO {
    @ApiModelProperty("评论ID")
    private Integer discussId;

    @ApiModelProperty("发布ID")
    private Integer publishId;

    @ApiModelProperty("发布标题")
    private String publishTitle;

    @ApiModelProperty("评论内容")
    private String discussContent;

    @ApiModelProperty("评论者信息")
    private UserDTO discussUserInfo;

    @ApiModelProperty("发布者信息")
    private UserDTO publishUserInfo;

    @ApiModelProperty("发布时间")
    private String publishCreateTime;

    @ApiModelProperty("评论时间")
    private String discussCreateTime;
}
