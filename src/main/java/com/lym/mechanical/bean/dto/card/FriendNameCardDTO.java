package com.lym.mechanical.bean.dto.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname FriendNameCardDTO
 * @Description
 * @Date 2019/11/7 16:31
 * @Created by jimy
 * good good code, day day up!
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendNameCardDTO {

    @ApiModelProperty(value = "历史添加")
    private List<NameCardSimpleDTO> historyCards;

    @ApiModelProperty(value = "搜索结果")
    private List<NameCardSimpleDTO> searchCards;
}
