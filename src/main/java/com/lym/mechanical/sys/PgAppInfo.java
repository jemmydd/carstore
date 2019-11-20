package com.lym.mechanical.sys;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author liyimin
 * @date 2019-11-05 09:06:21
 * good good code, day day up!
 */
@Data
@ConfigurationProperties(prefix = "pg-app-info")
@Configuration
public class PgAppInfo {

    private String appId;

    private String appSecret;

    private String mchId;

    private String serverIp;

    private String privateKey;

    private String callbackUrl;
}
