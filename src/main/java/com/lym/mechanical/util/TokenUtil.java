package com.lym.mechanical.util;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @author liyimin
 * @date 2019-11-05 09:06:21
 * good good code, day day up!
 */
@Slf4j
public class TokenUtil {
    public static String getToken(String account) {
        return SecureUtil.md5(account + DateFormatUtils.format(new Date(), DateFormatUtils.ISO_DATE_FORMAT.getPattern()));
    }
}
