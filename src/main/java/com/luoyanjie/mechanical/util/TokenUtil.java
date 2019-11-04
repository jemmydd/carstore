package com.luoyanjie.mechanical.util;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @author luoyanjie
 * @date 2019-08-19 19:53
 * Coding happily every day!
 */
@Slf4j
public class TokenUtil {
    public static String getToken(String account) {
        return SecureUtil.md5(account + DateFormatUtils.format(new Date(), DateFormatUtils.ISO_DATE_FORMAT.getPattern()));
    }
}
