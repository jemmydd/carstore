package com.luoyanjie.mechanical.bean.param.publish;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-08-15 09:12
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminPublishModifyParam {
    @ApiModelProperty("ID，用于修改")
    private Integer id;

    @ApiModelProperty("卖家ID")
    private Integer publishUserId;

    @ApiModelProperty("标题，必传")
    private String title;

    @ApiModelProperty("文本介绍，必传")
    private String textIntroduce;

    @ApiModelProperty("语音介绍，必传")
    private String voiceIntroduce;

    @ApiModelProperty("多媒体文件，最多8个，必传")
    private List<ImageVideosParam> imageVideos;

    @ApiModelProperty("联系电话，必传")
    private String contactPhone;

    @ApiModelProperty("出手价格，必传")
    private String outPrice;

    @ApiModelProperty("入手价格，必传")
    private String inPrice;

    @ApiModelProperty("一级类目，必传")
    private Integer firstCategoryId;

    @ApiModelProperty("二级类目，必传")
    private Integer secondCategoryId;

    @ApiModelProperty("生产年份，必传")
    private Integer productiveYear;

    //****** START
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
