package com.luoyanjie.mechanical.bean.dto.invite;

import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-25 16:27
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteDTO {
    @ApiModelProperty("邀请的人")
    private UserDTO invite;

    @ApiModelProperty("邀请时间")
    private String inviteTime;
}
