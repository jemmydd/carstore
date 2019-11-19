package com.lym.mechanical.util;

import com.lym.mechanical.bean.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * @author liyimin
 * @date 2019-11-05 09:06:21
 * good good code, day day up!
 */
@Slf4j
public class DateUtil {
    private static final String PREFIX_TODAY = "今天";
    private static final String PREFIX_TOMORROW = "明天";
    private static final String PREFIX_YESTERDAY = "昨天";
    private static final String PREFIX_YESTERDAY2 = "前天";
    private static final String TODAY = "(今天)";
    private static final String TOMORROW = "(明天)";
    private static final String LAST_POINT = "23:59";
    private static final Long ONE_MINITE = 60L;
    private static final Long ONE_HOUR = 60 * 60L;
    private static final Long ONE_DAY = 60 * 60 * 24L;

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

    public static String getDateStr(Date date) {
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
                    result = PREFIX_TODAY + dateStrArray[1];
                } else {//年月日不是当天
                    String yesterdayStr = now.plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
                    String[] yesterdayStrArray = yesterdayStr.split("-");

                    String yearMonthDayYesterday = yesterdayStrArray[0] + "-" + yesterdayStrArray[1] + "-" + yesterdayStrArray[2];

                    String yesterday2Str = now.plusDays(-2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
                    String[] yesterday2StrArray = yesterday2Str.split("-");

                    String yearMonthDay2Yesterday = yesterday2StrArray[0] + "-" + yesterday2StrArray[1] + "-" + yesterday2StrArray[2];

                    if (dateStr.startsWith(yearMonthDayYesterday)) {//是昨天
                        result = PREFIX_YESTERDAY + dateStrArray[1];
                    } else if (dateStr.startsWith(yearMonthDay2Yesterday)) {// 是前天
                        result = PREFIX_YESTERDAY2 + dateStrArray[1];
                    } else {
                        result = dateStrArray[0];
                    }
                }
            } else {//不同年
                result = dateStrArray[0];
            }
        }

        return result;
    }

    public static String formatDateDefault(Date date) {
        if (date == null) return "";

        return DateFormatUtils.format(date, Constant.DATE_FORMAT);
    }

    public static String formatDate(Date date, String format) {
        if (date == null) return "";

        return DateFormatUtils.format(date, format);
    }

    public static Date now() {
        return new Date();
    }

    /**
     * 判断当前是否在时间段内
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    public static Boolean dateValid(Date startTime, Date endTime) {
        if (Objects.isNull(startTime) || Objects.isNull(endTime)) {
            return Boolean.FALSE;
        }
        Date now = new Date();
        return now.compareTo(startTime) >= 0 && now.compareTo(endTime) <= 0;
    }

    public static String getTime(Long seconds) {
        if (Objects.isNull(seconds) || seconds == 0) {
            return "0秒";
        }
        if (seconds / ONE_MINITE < 1) {
            return seconds + "秒";
        } else if (seconds / ONE_HOUR < 1) {
            Long minutes = seconds / ONE_MINITE;
            Long restSeconds = seconds - minutes * ONE_MINITE;
            return minutes + "分" + getTime(restSeconds);
        } else if (seconds / ONE_DAY < 1) {
            Long hours = seconds / ONE_HOUR;
            Long restSeconds = seconds - hours * ONE_HOUR;
            return hours + "小时" + getTime(restSeconds);
        } else {
            return seconds + "秒";
        }
    }

    public static void main(String[] args) {
        System.out.println(getTime(5891L));
    }
}
