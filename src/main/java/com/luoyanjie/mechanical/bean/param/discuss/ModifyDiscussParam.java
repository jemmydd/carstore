package com.luoyanjie.mechanical.bean.param.discuss;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-08-14 23:13
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyDiscussParam {
    @ApiModelProperty("评论ID")
    private Integer id;

    @ApiModelProperty("评论内容")
    private String content;
}
