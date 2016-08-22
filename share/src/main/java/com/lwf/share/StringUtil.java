package com.lwf.share;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wenqiurong
 * @version [版本号, 2014-9-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class StringUtil {

    /**
     * 根据时间和格式转换为字符串
     *
     * @param pattern
     * @param date
     * @return
     */
    public static String getFormattTime(String pattern, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date == null ? new Date() : date);
    }

    /**
     * 处理时间
     *
     * @param timestamp
     * @return
     */
    public static String fixTime(String timestamp) {
        if (timestamp == null || "".equals(timestamp)) {
            return "";
        }

        try {
            long _timestamp = Long.parseLong(timestamp) * 1000;
            if (System.currentTimeMillis() - _timestamp < 1 * 60 * 1000) {
                return "刚刚";
            } else if (System.currentTimeMillis() - _timestamp < 30 * 60 * 1000) {
                return ((System.currentTimeMillis() - _timestamp) / 1000 / 60) + "分钟前";
            } else {
                Calendar now = Calendar.getInstance();
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(_timestamp);
                if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH) && c
                    .get(Calendar.DATE) == now.get(Calendar.DATE)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("今天 HH:mm");
                    return sdf.format(c.getTime());
                }
                if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH) && c
                    .get(Calendar.DATE) == now.get(Calendar.DATE) - 1) {
                    SimpleDateFormat sdf = new SimpleDateFormat("昨天 HH:mm");
                    return sdf.format(c.getTime());
                } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("M月d日 HH:mm:ss");
                    return sdf.format(c.getTime());
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日 HH:mm:ss");
                    return sdf.format(c.getTime());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 参数编码，默认为utf-8
     *
     * @param s
     * @return
     */
    public static String encode(String s) {
        if (s == null) {
            return "";
        }
        return URLEncoder.encode(s);
    }

    /**
     * 参数反编码,默认为utf-8
     *
     * @param s
     * @return
     */
    public static String decode(String s) {
        if (s == null) {
            return "";
        }
        return URLDecoder.decode(s);
    }

    /**
     * @param parameters
     * @return
     */
    public static String map2String(HashMap<String, String> parameters) {
        StringBuilder paras = new StringBuilder();
        if (parameters != null) {
            Set<String> keys = parameters.keySet();
            for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
                String key = i.next();
                paras.append(key + "=" + parameters.get(key) + "&");
            }
        }

        return paras.toString();
    }

    /**
     * @param parameters
     * @return
     */
    public static HashMap<String, String> String2Map(String parameters) {
        HashMap<String, String> parametersMap = new HashMap<String, String>();
        String[] StrArray = parameters.replace("?", "").split("&");
        for (int i = 0; i < StrArray.length; i++) {
            String[] item = StrArray[i].split("=");
            if (item.length == 2) {
                parametersMap.put(item[0], item[1]);
            }
        }
        return parametersMap;
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 判断是否是电话
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        // Pattern phonePattern = Pattern.compile("^1\\d{10}$");
        Pattern phonePattern = Pattern.compile("(^1\\d{10})$|(^(0[0-9]{2,3}-)([2-9][0-9]{6,7})+(/-[0-9]{1,4})?$)");
        Matcher matcher = phonePattern.matcher(phone);
        return matcher.find();
    }

    public static boolean isIDCard(String idNum) {
        Pattern phonePattern = Pattern.compile("^(\\d{14}|\\d{17})(\\d|[xX])$");

        Matcher matcher = phonePattern.matcher(idNum);

        return matcher.find();

    }

    public static String phoneFormat(String phone) {
        if (isEmpty(phone) || phone.length() < 11) {
            return phone;
        } else {
            return phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
        }

    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {

        if (input == null) {
            return input;
        }

        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char)32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char)(c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 根据输入流返回txt内容
     *
     * @param @param  inputStream
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public static String getStringByFileName(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {

            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {

        if (TextUtils.isEmpty(source)) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (!isNormalCharacter(codePoint)) {
                // do nothing，判断到了这里表明，确认有表情字符

                LogUtils.e("Emoji", "container emoji[" + codePoint + "]");

                return true;
            } else {
                LogUtils.e("Emoji", "normal character[" + codePoint + "]");
            }
        }

        return false;
    }

    private static boolean isNormalCharacter(char codePoint) {

        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));

    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {

        if (!containsEmoji(source)) {
            return source;// 如果不包含，直接返回
        }
        // 到这里铁定包含
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isNormalCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            } else {
            }
        }

        if (buf == null) {
            return source;// 如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }

    }

}
