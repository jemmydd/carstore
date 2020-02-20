package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liyimin
 * @create 2020-02-20 16:45
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminLookRecordDTO {

    @ApiModelProperty(value = "车商信息")
    private CarUserDTO user;

    @ApiModelProperty(value = "最近浏览时间")
    private String laseTime;
}
