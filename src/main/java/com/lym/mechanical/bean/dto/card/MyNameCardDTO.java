package com.lym.mechanical.bean.dto.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname MyNameCardDTO
 * @Description 我的名片
 * @Date 2019/11/6 17:35
 * @Created by jimy
 * good good code, day day up!
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MyNameCardDTO {

    @ApiModelProperty(value = "是否有名片")
    private Boolean hasCard;

    @ApiModelProperty(value = "车商申请状态,0-未申请，1-申请中，2-申请通过")
    private String applyStatus;

    @ApiModelProperty(value = "名片信息")
    private NameCardDTO nameCard;

    @ApiModelProperty(value = "推荐名片列表")
    private List<NameCardDTO> refereeCards;

    @ApiModelProperty(value = "最近访客列表")
    private List<RecentlyUserDTO> recentlyUsers;
}
