package com.luoyanjie.mechanical.bean.dto.my;

import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-04 16:45
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FansDTO {
    @ApiModelProperty("关注ID")
    private Integer id;

    @ApiModelProperty("信息")
    private UserDTO personInfo;

    @ApiModelProperty("是否被请求者关注了")
    private Boolean followed;
}
