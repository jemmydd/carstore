package com.lym.mechanical.bean.dto.publish;

import com.lym.mechanical.bean.dto.user.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-08-06 19:14
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishDiscussDTO {
    @ApiModelProperty("动态的ID")
    private Integer id;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("友好的创建时间")
    private String createTimeFriendly;

    @ApiModelProperty("评论者")
    private Integer userId;

    @ApiModelProperty("评论者信息")
    private UserDTO userInfo;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("发布ID")
    private Integer publishId;

    @ApiModelProperty("发布的发布者ID")
    private Integer publishUserId;

    @ApiModelProperty("发布者的读取状态")
    private Byte publishUserReadStatus;

    @ApiModelProperty("主楼ID")
    private Integer floorDiscussId;

    @ApiModelProperty("主楼发布者ID")
    private Integer floorDiscussUserId;

    @ApiModelProperty("被回复的动态的ID")
    private Integer beReplyDiscussId;

    @ApiModelProperty("被回复的动态的评论者ID")
    private Integer beReplyReplyDiscussUserId;

    @ApiModelProperty("内容的前缀，比如回复xxx：")
    private String contentPrefix;

    @ApiModelProperty("二级")
    private List<PublishDiscussDTO> secondLevel;
}
