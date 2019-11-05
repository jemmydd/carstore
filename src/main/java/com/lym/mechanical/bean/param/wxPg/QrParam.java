package com.lym.mechanical.bean.param.wxPg;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 详见：https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/qr-code/wxacode.getUnlimited.html#method-http
 *
 * @author luoyanjie
 * @date 2019-09-03 20:15
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrParam {
    @ApiModelProperty("最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）")
    private String scene;

    @ApiModelProperty("必须是已经发布的小程序存在的页面（否则报错），例如 pages/index/index, 根路径前不要填加 /,不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面")
    private String page;

    @ApiModelProperty("二维码的宽度，单位 px，最小 280px，最大 1280px")
    private String width;

    @ApiModelProperty("自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调，默认 false")
    private String auto_color;

    @ApiModelProperty("auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {\"r\":\"xxx\",\"g\":\"xxx\",\"b\":\"xxx\"} 十进制表示")
    private String line_color;

    @ApiModelProperty("是否需要透明底色，为 true 时，生成透明底色的小程序")
    private String is_hyaline;
}
