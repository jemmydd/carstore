package com.luoyanjie.mechanical.component.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.luoyanjie.mechanical.bean.enumBean.EnvEnum;
import com.luoyanjie.mechanical.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author luoyanjie
 * @date 2019-07-29 16:35
 * Coding happily every day!
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {
    @Autowired
    private OSSClient ossClient;

    @Value("${spring.profiles.active}")
    private String active;

    private interface Bucket {
        String PROD = "mechanical";
        String TEST = "mechanical-test";
    }

    @Override
    public String upload(String key, InputStream inputStream) {
        PutObjectResult putObjectResult = ossClient.putObject(EnvEnum.isProd(active) ? Bucket.PROD : Bucket.TEST, key, inputStream);

        log.info("上传了文件 key = [ {} ], result = [ {} ]", key, GsonUtil.GSON.toJson(putObjectResult));

        return key;
    }

    @Override
    public String upload(String key, InputStream inputStream, String bucketName) {
        PutObjectResult putObjectResult = ossClient.putObject(bucketName, key, inputStream);

        log.info("上传了文件 key = [ {} ], result = [ {} ]", key, GsonUtil.GSON.toJson(putObjectResult));

        return key;
    }

    public static String key() {
        return UUID.randomUUID() + RandomStringUtils.randomAlphanumeric(10);
    }
}
