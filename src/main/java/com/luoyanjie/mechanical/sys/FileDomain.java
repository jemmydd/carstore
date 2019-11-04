package com.luoyanjie.mechanical.sys;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author luoyanjie
 * @date 2019-07-28 09:49
 * Coding happily every day!
 */
@Data
@ConfigurationProperties(prefix = "file-domain")
@Configuration
public class FileDomain {
    private String aliOssDomain;//域名，结尾带/

    /**
     * 给资源加上域名
     *
     * @param original 原始路径
     * @return 全路径域名
     */
    public String addDomain(String original) {
        String result = "no original";

        if (StringUtils.isNotBlank(original)) {
            while (original.startsWith("/")) {
                original = original.substring(1);
            }

            result = aliOssDomain + original;
        }

        return result;
    }

    public static String addDomain(String original, String fileUrl) {
        String result = "no original";

        if (StringUtils.isNotBlank(original)) {
            while (original.startsWith("/")) {
                original = original.substring(1);
            }

            result = fileUrl + original;
        }

        return result;
    }
}
