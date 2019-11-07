package com.lym.mechanical.bean.dto.location;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDetailDTO {
    @ApiModelProperty("位置")
    private String location;

    @ApiModelProperty("省份编码")
    private String provinceCode;

    @ApiModelProperty("省份名称")
    private String provinceName;

    @ApiModelProperty("城市编码")
    private String cityCode;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("区编码")
    private String areaCode;

    @ApiModelProperty("区名称")
    private String areaName;
}
