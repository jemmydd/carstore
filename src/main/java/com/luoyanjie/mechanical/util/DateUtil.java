package com.luoyanjie.mechanical.util;

import com.luoyanjie.mechanical.bean.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author luoyanjie
 * @date 2019-08-04 16:22
 * Coding happily every day!
 */
@Slf4j
public class DateUtil {
    private static final String PREFIX_TODAY = "今天";
    private static final String PREFIX_TOMORROW = "明天";
    private static final String TODAY = "(今天)";
    private static final String TOMORROW = "(明天)";
    private static final String LAST_POINT = "23:59";

    public static String formatDate(Date date) {
        String result = "";

        if (date != null) {
            String dateStr = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm");
            String[] dateStrArray = dateStr.split(" ");
            String[] dateStrArray0Array = dateStrArray[0].split("-");

            LocalDateTime now = LocalDateTime.now();
            String nowStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
            String[] nowStrArray = nowStr.split("-");

            if (dateStr.startsWith(nowStrArray[0])) {//同一年
                String yearMonthDay = nowStrArray[0] + "-" + nowStrArray[1] + "-" + nowStrArray[2];

                if (dateStr.startsWith(yearMonthDay)) {//年月日为当天
                    if (LAST_POINT.equals(dateStrArray[1])) {
                        result = PREFIX_TODAY;
                    } else {
                        result = dateStrArray[1] + TODAY;
                    }
                } else {//年月日不是当天
                    String tomorrowStr = now.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
                    String[] tomorrowStrArray = tomorrowStr.split("-");

                    String yearMonthDayTomorrow = tomorrowStrArray[0] + "-" + tomorrowStrArray[1] + "-" + tomorrowStrArray[2];

                    String todayMonthDay = dateStrArray0Array[1] + "-" + dateStrArray0Array[2];

                    if (dateStr.startsWith(yearMonthDayTomorrow)) {//是明天
                        if (LAST_POINT.equals(dateStrArray[1])) {
                            result = todayMonthDay + TOMORROW;
                        } else {
                            result = todayMonthDay + " " + dateStrArray[1] + TOMORROW;
                        }
                    } else {//不是明天
                        if (LAST_POINT.equals(dateStrArray[1])) {
                            result = todayMonthDay;
                        } else {
                            result = todayMonthDay + " " + dateStrArray[1];
                        }
                    }
                }
            } else {//不同年
                if (LAST_POINT.equals(dateStrArray[1])) {
                    result = dateStrArray[0];
                } else {
                    result = dateStr;
                }
            }
        }

        return result;
    }

    public static String formatDateDefault(Date date) {
        if (date == null) return "";

        return DateFormatUtils.format(date, Constant.DATE_FORMAT);
    }
}
