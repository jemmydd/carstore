package com.lym.mechanical.bean.param.feedback;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname FeedbackParam
 * @Description
 * @Date 2020/1/14 18:21
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackParam {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "反馈内容")
    private String content;

    @ApiModelProperty(value = "反馈图片")
    private List<String> images;
}
