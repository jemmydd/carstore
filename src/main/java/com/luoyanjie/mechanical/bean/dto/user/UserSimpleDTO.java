package com.luoyanjie.mechanical.bean.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-15 10:36
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleDTO {
    @ApiModelProperty("用户ID")
    private Integer id;

    @ApiModelProperty("昵称")
    private String nickName;
}
