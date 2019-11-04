package com.luoyanjie.mechanical.bean.dto.publish;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-31 17:07
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishSubmitDTO {
    @ApiModelProperty("是否成功")
    private Boolean succeed;

    @ApiModelProperty("发布成功后的ID")
    private Integer id;
}
