package com.lym.mechanical.bean.dto.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname ChangeNameCardDTO
 * @Description
 * @Date 2019/11/7 14:13
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeNameCardDTO {

    @ApiModelProperty(value = "最近浏览过的一张名片")
    private NameCardDTO lookCard;

    @ApiModelProperty(value = "推荐名片")
    private List<NameCardDTO> refereeCards;
}
