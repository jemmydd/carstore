package com.luoyanjie.mechanical.bean.dto.my;

import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-07-30 09:10
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OverviewDTO {
    @ApiModelProperty("概览用户的信息")
    private UserDTO userDTO;

    @ApiModelProperty("头像")
    private String headPortrait;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("个性签名")
    private String signature;

    @ApiModelProperty("我的关注数")
    private Integer followCount;

    @ApiModelProperty("我的粉丝数")
    private Integer fansCount;

    @ApiModelProperty("我的发布数")
    private Integer publishCount;

    @ApiModelProperty("我的收藏数")
    private Integer collectionCount;

    @ApiModelProperty("我的未读动态数")
    private Long unreadCommentCount;
}
