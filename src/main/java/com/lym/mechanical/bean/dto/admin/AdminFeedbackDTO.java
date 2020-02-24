package com.lym.mechanical.bean.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liyimin
 * @create 2020-02-24 15:46
 * Good good code, day day up!
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminFeedbackDTO {

    @ApiModelProperty(value = "反馈id")
    private Integer id;

    @ApiModelProperty(value = "反馈内容")
    private String content;

    @ApiModelProperty(value = "反馈图片")
    private List<String> images;

    @ApiModelProperty(value = "反馈时间")
    private String createTime;

    @ApiModelProperty(value = "处理内容")
    private String dealMessage;

    @ApiModelProperty(value = "处理时间")
    private String dealTime;
}
