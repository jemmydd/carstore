package com.lym.mechanical.bean.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UnifiedOrderDTO implements Serializable {
    //公众账号ID  应用ID
    private String appid;

    //商户号
    private String mch_id;

    //子商户公众账号ID(否)   子商户应用ID
    private String sub_appid;

    //子商户号
    private String sub_mch_id;

    //设备号(否)
    private String device_info;

    //随机字符串
    private String nonce_str;

    //签名
    private String sign;

    //签名类型(否)
    private String sign_type;

    //商品描述
    private String body;

    //商品详情(否)
    private String detail;

    //附加数据(否)
    private String attach;

    //商户订单号
    private String out_trade_no;

    //标价币种(否)
    private String fee_type;

    //标价金额
    private String total_fee;

    //终端IP
    private String spbill_create_ip;

    //交易起始时间(否)
    private String time_start;

    //交易结束时间(否)
    private String time_expire;

    //订单优惠标记(否)
    private String goods_tag;

    //通知地址
    private String notify_url;

    //交易类型
    private String trade_type;

    //商品ID(否)
    private String product_id;

    //指定支付方式(否)
    private String limit_pay;

    //用户标识(否)
    private String openid;

    //用户子标识(否)
    private String sub_openid;

    //场景信息(否) 暂时不用
    private String scene_info;

}
