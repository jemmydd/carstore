package com.luoyanjie.mechanical.component.oss;

import java.io.InputStream;

/**
 * @author luoyanjie
 * @date 2019-07-29 16:23
 * Coding happily every day!
 */
public interface OssService {
    String upload(String key, InputStream inputStream);

    String upload(String key, InputStream inputStream, String bucketName);
}
