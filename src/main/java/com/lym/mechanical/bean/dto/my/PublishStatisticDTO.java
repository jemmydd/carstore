package com.lym.mechanical.bean.dto.my;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname PublishStatisticDTO
 * @Description
 * @Date 2019/11/18 14:29
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublishStatisticDTO {

    @ApiModelProperty(value = "今日访问次数")
    private Integer todayGuest;

    @ApiModelProperty(value = "累计访问次数")
    private Integer totalGuest;

    @ApiModelProperty(value = "是否VIP")
    private Boolean isVip;

    @ApiModelProperty(value = "商品浏览记录")
    private List<PublishLookRecordDTO> lookRecords;
}
