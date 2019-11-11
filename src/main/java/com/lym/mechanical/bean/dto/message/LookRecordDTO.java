package com.lym.mechanical.bean.dto.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname LookRecordDTO
 * @Description
 * @Date 2019/11/11 11:09
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LookRecordDTO {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "名片id，查看名片才会有")
    private Integer cardId;

    @ApiModelProperty(value = "设备id，设备操作才会有")
    private Integer publishId;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "时间")
    private String time;

    @ApiModelProperty(value = "描述")
    private String desc;
}
