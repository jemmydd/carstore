package com.lym.mechanical.component.oss;

import com.aliyun.oss.OSSClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.annotation.MultipartConfig;

/**
 * @author liyimin
 * @date 2019-11-05 09:06:21
 * good good code, day day up!
 */
@Configuration
@PropertySource(value = {"classpath:oss.properties"}, ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "oss")
@MultipartConfig(maxFileSize = 10000000, maxRequestSize = 10000000) //上限10m
public class OssConfig {
    @Setter
    @Getter
    String endpoint;

    @Setter
    @Getter
    String accessKeyId;

    @Setter
    @Getter
    String accessKeySecret;

    @Bean
    public OSSClient ossClient() {
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }
}
