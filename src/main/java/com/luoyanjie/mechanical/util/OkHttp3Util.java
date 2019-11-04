package com.luoyanjie.mechanical.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-07-30 16:13
 * Coding happily every day!
 */
@Slf4j
public class OkHttp3Util {
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder().build();
    private static final String USER_AGENT = "User-Agent";
    private static final List<String> USER_AGENT_LIST = Lists.newArrayList(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:57.0) Gecko/20100101 Firefox/57.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/604.3.5 (KHTML, like Gecko) Version/11.0.1 Safari/604.3.5",
            "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/10.3 Mobile/14E277 Safari/603.1.30",
            "Mozilla/5.0 (iPod; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/10.3 Mobile/14E277 Safari/603.1.30",
            "Mozilla/5.0 (iPad; CPU OS 10_3 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/10.3 Mobile/14E277 Safari/603.1.30",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/13.10586",
            "Mozilla/5.0 (Windows NT 6.3; Win64, x64; Trident/7.0; rv:11.0) like Gecko",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:52.0) Gecko/20100101 Firefox/52.0"
    );

    public static String simpleGet(String url) throws IOException {
        log.info("url = [ {} ]", url);
        Request request = new Request.Builder()
                .headers(getRandomUserAgentHeaders())
                .url(url)
                .get()
                .build();//构造请求
        Response response = CLIENT.newCall(request).execute();//执行请求，获得响应


        ResponseBody responseBody = response.body();

        if (responseBody == null) {
            log.info("url = [ {} ], 没有得到数据", url);
            return null;
        }

        return responseBody.string();
    }

    /**
     * 获取一个简单的请求头
     *
     * @return 设置了随机UA的请求头
     */
    private static Headers getRandomUserAgentHeaders() {
        return setRandomUserAgent(new Headers.Builder().build());
    }

    /**
     * 给请求头设置一个随机的UA
     *
     * @param headers 请求头
     * @return 设置好UA的请求头
     */
    private static Headers setRandomUserAgent(Headers headers) {
        Headers.Builder builder = headers.newBuilder();
        builder.add(USER_AGENT, getRandomUserAgent());
        return builder.build();
    }

    /**
     * 获取一个随机的UA
     *
     * @return 随机UA
     */
    private static String getRandomUserAgent() {
        return USER_AGENT_LIST.get(RandomUtils.nextInt(0, USER_AGENT_LIST.size() - 1));
    }
}
