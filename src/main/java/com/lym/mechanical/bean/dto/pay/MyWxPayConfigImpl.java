package com.lym.mechanical.bean.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class MyWxPayConfigImpl extends WXPayConfig {
    private String appId;
    private String mchId;
    private String key;
    private byte[] certData;

    @Override
    String getAppID() {
        return this.appId;
    }

    @Override
    String getMchID() {
        return this.mchId;
    }

    @Override
    String getKey() {
        return this.key;
    }

    @Override
    InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

}
