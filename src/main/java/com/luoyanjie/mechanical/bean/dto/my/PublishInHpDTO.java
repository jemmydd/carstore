package com.luoyanjie.mechanical.bean.dto.my;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-08-03 21:43
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishInHpDTO {
    @ApiModelProperty("日期-格式化为20190101")
    private Integer date;

    @ApiModelProperty("日期：今天、明天、25")
    private String dayText;

    @ApiModelProperty("月份")
    private String monthText;

    @ApiModelProperty("年份")
    private String yearText;

    @ApiModelProperty("内容项")
    private List<PublishInHpItemDTO> items;
}
