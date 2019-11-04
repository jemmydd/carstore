package com.luoyanjie.mechanical.bean.param.wxPg;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author luoyanjie
 * @date 2019-09-07 19:08
 * Coding happily every day!
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateSendParam {
    @ApiModelProperty("接收者（用户）的 自由系统的用户ID")
    private Integer toUserId;

    @ApiModelProperty("接收者（用户）的 openid")
    private String touser;

    @ApiModelProperty("下发的模板ID")
    private String template_id;

    @ApiModelProperty("点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。")
    private String page;

    @ApiModelProperty("表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id")
    private String form_id;

    @ApiModelProperty("模板内容，不填则下发空模板。具体格式请参考示例")
    private List<TemplateSendDataParam> data;

    @ApiModelProperty("模板需要放大的关键词，不填则默认无放大")
    private String emphasis_keyword;
}
