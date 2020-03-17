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
@ConfigurationProperties(prefix = "tencentyun")
@Configuration
public class TencentYunInfo {

    private Long sdkappid;

    private String key;

    private String manager;
}
