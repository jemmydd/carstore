package com.luoyanjie.mechanical.bean.dto.my.v2;

import com.luoyanjie.mechanical.bean.dto.publish.PublishDTO;
import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import com.luoyanjie.mechanical.component.result.PageData;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-22 19:35
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalHomePageV2DTO {
    @ApiModelProperty("需要获取的主页者就是请求者吗？true是，false不是")
    private Boolean whoIsRequestUser;

    @ApiModelProperty("关注状态，只有在不是请求者的个人主页里面才有效")
    private Boolean followed;

    @ApiModelProperty("主页者信息")
    private UserDTO whoInfo;

    @ApiModelProperty("发布的内容列表，只返回第一页数据")
    private PageData<PublishDTO> publishInHp;
}
