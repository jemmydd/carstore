package com.luoyanjie.mechanical.sys;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author luoyanjie
 * @date 2019-08-31 23:34
 * Coding happily every day!
 */
@Data
@ConfigurationProperties(prefix = "pg-app-info")
@Configuration
public class PgAppInfo {
    private String appId;

    private String appSecret;
}
