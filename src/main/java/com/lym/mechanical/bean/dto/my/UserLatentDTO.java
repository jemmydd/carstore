package com.lym.mechanical.bean.dto.my;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname UserLatentDTO
 * @Description
 * @Date 2020/1/17 11:43
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLatentDTO {

    @ApiModelProperty(value = "用户")
    private LatentUserDTO user;

    @ApiModelProperty(value = "设备列表")
    private List<LatentPublishStatisticDTO> publishs;
}
