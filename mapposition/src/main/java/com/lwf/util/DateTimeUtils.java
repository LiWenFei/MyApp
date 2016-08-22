package com.lwf.util;

import android.content.Context;
import android.text.TextUtils;

import com.lwf.mapposition.R;
import com.lwf.share.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间格式化类
 *
 * @author liuweiping
 * @since 16/7/28
 */
public class DateTimeUtils {
    public final static String DATE_PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    public final static String DATE_PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public final static String DATE_PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_PATTERN_HH_MM_SS = "HH:mm:ss";
    public final static String DATE_PATTERN_HH_MM = "HH:mm";
    public final static String DATE_PATTERN_YY_M_D = "yy/M/d";
    public final static String DATE_PATTERN_MM_DD = "MM-dd";
    public final static String DATE_PATTERN_MM_DD_HH_MM = "MM-dd HH:mm";

    /**
     * 获取友好的时间格式
     * <p>
     * 5分钟内：现在 <br/>
     * 1小时内：刚刚 <br/>
     * 1小时前：HH:mm <br/>
     * 0点前：昨天 <br/>
     * 7天内：星期W <br/>
     * 其他：yy/M/d <br/>
     *
     * @param context    Context
     * @param timeMillis 时间
     * @return 友好的时间格式
     */
    public static String getFriendlyTime(Context context, long timeMillis) {
        if (context == null)
            return null;

        long dTime = System.currentTimeMillis() - timeMillis;
        // 未来
        if (dTime < 0)
            return getFormatData(timeMillis, DATE_PATTERN_YY_M_D);
        // 5分钟内
        if (dTime < 5 * 60 * 1000)
            return context.getString(R.string.date_now);
        // 1小时内
        if (dTime < 60 * 60 * 1000)
            return context.getString(R.string.date_just_now);

        // 判断今天/昨天/本周/更早
        Calendar last = Calendar.getInstance();
        last.setFirstDayOfWeek(Calendar.MONDAY);
        last.setTimeInMillis(timeMillis);
        // 今天
        Calendar temp = Calendar.getInstance();
        temp.setFirstDayOfWeek(Calendar.MONDAY);
        temp.set(Calendar.MILLISECOND, 0);
        temp.set(Calendar.SECOND, 0);
        temp.set(Calendar.MINUTE, 0);
        temp.set(Calendar.HOUR_OF_DAY, 0);
        if (!last.before(temp))
            return getHMData(timeMillis);
        // 昨天
        temp.add(Calendar.DAY_OF_YEAR, -1);
        if (!last.before(temp))
            return context.getString(R.string.date_yesterday);
        // 一周内
        temp.add(Calendar.DAY_OF_YEAR, -5);
        if (!last.before(temp)) {
            int week = last.get(Calendar.DAY_OF_WEEK) - 1;
            return context.getResources().getStringArray(R.array.week_name)[week % 7];
        }
        // 今年
        temp.set(Calendar.DAY_OF_YEAR, 0);
        if (!last.before(temp)) {
            return getFormatData(timeMillis, DATE_PATTERN_MM_DD);
        }
        return getFormatData(timeMillis, DATE_PATTERN_YYYY_MM_DD);
    }

    /**
     * 格式化时间
     *
     * @param timeMillis 时间毫秒值.
     * @param pattern    格式, 可以为null使用默认的格式 yyyy-MM-dd HH:mm:ss
     * @return 格式化时间
     */
    public static String getFormatData(long timeMillis, String pattern) {
        if (TextUtils.isEmpty(pattern))
            pattern = DATE_PATTERN_YYYY_MM_DD_HH_MM_SS;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date curDate = new Date(timeMillis);
        return formatter.format(curDate);
    }

    public static String getHMSData(long milliseconds) {
        SimpleDateFormat sf = new SimpleDateFormat(DATE_PATTERN_HH_MM_SS);
        Date d = new Date(milliseconds);
        return sf.format(d);
    }

    public static String getHMData(long milliseconds) {
        SimpleDateFormat sf = new SimpleDateFormat(DATE_PATTERN_HH_MM);
        Date d = new Date(milliseconds);
        return sf.format(d);
    }

    /**
     * 将字符串转为时间戳.
     * <p>
     * 2016-08-12 21:36:51
     *
     * @param time
     * @return
     */
    public static long parseString2Milliseconds(String time) {
        return parseString2Milliseconds(time, DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 将字符串转为时间戳.
     *
     * @param time
     * @param pattern 例如 DATE_PATTERN_YYYY_MM_DD_HH_MM_SS
     * @return
     */
    public static long parseString2Milliseconds(String time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(time).getTime();
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return 0;
    }

    /**
     * 截取时间到天
     *
     * @param create_time
     * @return
     */
    public static String getCreateTimeDay(String create_time) {
        String mCreateTime = "";
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(create_time));
            Date date = new Date(c.getTimeInMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            mCreateTime = sdf.format(date);
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return mCreateTime;
    }

    /**
     * 判断 timeMillis 是否在今天之前。
     *
     * @param timeMillis
     * @return true - 今天0点之前；false - 今天0点之后
     */
    public static boolean isBeforeToday(long timeMillis) {
        // 获取今天开始的时间
        Calendar beginOfToday = Calendar.getInstance();
        beginOfToday.setTimeInMillis(System.currentTimeMillis());
        beginOfToday.set(Calendar.MILLISECOND, 0);
        beginOfToday.set(Calendar.SECOND, 0);
        beginOfToday.set(Calendar.MINUTE, 0);
        beginOfToday.set(Calendar.HOUR_OF_DAY, 0);

        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(timeMillis);
        return time.before(beginOfToday);
    }

}
