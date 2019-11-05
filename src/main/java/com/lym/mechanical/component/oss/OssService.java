package com.lym.mechanical.component.oss;

import java.io.InputStream;

/**
 * @author liyimin
 * @date 2019-11-05 09:06:21
 * good good code, day day up!
 */
public interface OssService {
    String upload(String key, InputStream inputStream);

    String upload(String key, InputStream inputStream, String bucketName);
}
