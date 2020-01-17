package com.lym.mechanical.bean.dto.my;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname LatentDTO
 * @Description
 * @Date 2020/1/17 11:30
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LatentDTO {

    @ApiModelProperty(value = "设备信息")
    private LatentUserPublishDTO publish;

    @ApiModelProperty(value = "历史访问用户")
    private List<LookUserDTO> users;
}
