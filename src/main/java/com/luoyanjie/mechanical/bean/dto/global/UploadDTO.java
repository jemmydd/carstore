package com.luoyanjie.mechanical.bean.dto.global;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luoyanjie
 * @date 2019-07-29 19:05
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadDTO {
    @ApiModelProperty("上传结果")
    private Boolean uploadResult;

    @ApiModelProperty("上传的文件的原始的名字")
    private String originalFilename;

    @ApiModelProperty("上传后的文件地址")
    private String url;

    @ApiModelProperty("上传后的文件地址+域名拼接")
    private String domainUrl;
}
