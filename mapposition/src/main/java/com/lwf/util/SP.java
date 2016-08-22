package com.lwf.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.text.TextUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 利用SharePreferenceDataManager统一大家利用SharePreference完成键值的存储操作。
 * 现有的xml文件有5个 ， 每个xml文件中又包含各自的键值 。 按照键所属的xml文件和键值的类型进行归类 。键值一共有6种类型 ，
 * boolean 、int 、long、String、Float以及StringSet
 */
public class SP {

    /**
     * 文字间隔符。
     */
    public static final String STRING_SPLITER = ";;";

    /**
     * 定义用于用户系统的xml的文件名称，以及xml文件中的所有键名称与对应的默认键值
     */
    public static class UserXml {
        // xml文件名称
        public static final String XML_NAME = "Passport";
        /**
         * 上次登录的用户ID。在用户登录成功后修改此值，用户退出后设置此值为默认值。
         */
        public static final KEY<Integer> KEY_LAST_LOGIN_UID = new KEY<>("login_uid", -1);
        /**
         * 曾经登录的手机号, 用于输入提示
         */
        public static final KEY<String> KEY_HISTORY_PHONENUM = new KEY<>("history_phonenum", "");
        /**
         * 上次登录的用户token令牌。在用户登录成功后修改此值，用户退出后设置此值为默认值。
         */
        public static final KEY<String> KEY_LAST_LOGIN_SESSION = new KEY<>("login_token", null);
        /**
         * 上次登录的用户信息JSON
         */
        public static final KEY<String> KEY_LAST_JSON = new KEY<>("login_json", null);
        /**
         * 上次登录使用的服务器地址
         */
        public static final KEY<String> KEY_LAST_API_HOST = new KEY<>("last_api_host", null);
        /**
         * 上次登录账号的主屏图片
         */
        public static final KEY<String> KEY_LAST_SCREEN = new KEY<>("campus_screen", null);
        public static final KEY<String> KEY_LAST_CAMPUS_JSON = new KEY<>("login_campus_id", null);
    }

    /**
     * 定义用于推送的xml的文件名称，以及xml文件中的所有键名称与对应的默认键值
     */
    public static class PushXml {
        // xml文件名称
        public static final String XML_NAME = "Push";
        /**
         * 推送用户注册的唯一标识(clientID)
         */
        public static final KEY<String> KEY_PUSH_TOKEN = new KEY<>("push_token", null);
        //        public static final KEY<Integer> KEY_PUSH_SYSTEM_MSG_COUNT = new KEY<>("push_system_msg_count", 0);
        public static final KEY<Integer> KEY_PUSH_SSP_MSG_COUNT = new KEY<>("push_ssp_msg_count", 0);
        public static final KEY<Integer> KEY_PUSH_REPAIR_MSG_COUNT = new KEY<>("push_repair_msg_count", 0);
        public static final KEY<Integer> KEY_PUSH_ANNOUNCE_MSG_COUNT = new KEY<>("push_system_msg_count", 0);
    }

    /**
     * 定义用于各种系统配置的xml的文件名称，以及xml文件中的所有键名称与对应的默认键值
     */
    public static class PrefsXml {
        // xml文件名称
        public static final String XML_NAME = "Preferences";
        public static final KEY<Boolean> KEY_IS_FIRST_WELCOME = new KEY<>("is_first_welcome", true);
    }

    /**
     * 定义用于应用自更新的xml的文件名称，以及xml文件中的所有键名称与对应的默认键值
     */
    public static class AppUpgradeXml {
        // xml文件名称
        public static final String XML_NAME = "AppUpgrade";
//        /**
//         * 只保存新版的版本号，据此判断是否有新版
//         */
//        public static final KEY<Integer> KEY_NEW_VERSION = new KEY<>("has_new_version", 0);
        /**
         * 用户取消更新的版本号，有新版本号才弹出更新
         */
        public static final KEY<Integer> KEY_CANCELED_VERSION = new KEY<>("canceled_version", 0);
//        /**
//         * 上次有更新时的JSON字符串
//         */
//        public static final KEY<String> KEY_UPGRADE_JSON = new KEY<>("upgrade_json", null);
//        public static final KEY<Long> KEY_CANCEL_TIME = new KEY<>("last_cancel_time", 0L);

    }

    /**
     * 定义用于Settings的xml的文件名称，以及xml文件中的所有键名称与对应的默认键值
     */
    public static class SettingsXml {
        // xml文件名称
        public static final String XML_NAME = "Settings";
//        /**
//         * 同时下载任务数
//         */
//        public static final KEY<Integer> KEY_SETTING_TASK_NUM = new KEY<>("setting_task_num", 2);
    }

    /**
     * 定义用于搜索历史记录的xml的文件名称，以及xml文件中的所有键名称与对应的默认键值
     */
    public static class SearchXml {
        // xml文件名称
        public static final String XML_NAME = "Searched";
        /**
         * 搜索历史记录
         */
        public static final KEY<String> KEY_LAST_SEARCH_HISTORY = new KEY<>("search_history", "");

    }

    /**
     * xml文件中键值的get和set方法,方法中需要传入4个参数Context、xml文件的名称xmlName、
     * 键名称keyName以及键值keyValue
     *
     * @param context
     * @param xmlName
     * @param key
     * @param defaultValue
     * @return
     */

    public static boolean getBoolean(Context context, String xmlName, String key, boolean defaultValue) {
        SharedPreferences settingPre = getShareModeRead(context, xmlName);
        return null == settingPre ? defaultValue : settingPre.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context context, String xmlName, String key, boolean value) {

        SharedPreferences settingPre = getShareModeWrite(context, xmlName);
        if (null != settingPre) {
            settingPre.edit().putBoolean(key, value).apply();
        }
    }

    public static int getInt(Context context, String xmlName, String key, int defaultValue) {
        SharedPreferences settingPre = getShareModeRead(context, xmlName);
        return null == settingPre ? defaultValue : settingPre.getInt(key, defaultValue);
    }

    public static void setInt(Context context, String xmlName, String key, int value) {
        SharedPreferences settingPre = getShareModeWrite(context, xmlName);
        if (settingPre != null) {
            settingPre.edit().putInt(key, value).apply();
        }
    }

    public static long getLong(Context context, String xmlName, String key, long defaultValue) {
        SharedPreferences settingPre = getShareModeRead(context, xmlName);
        return null == settingPre ? defaultValue : settingPre.getLong(key, defaultValue);
    }

    public static void setLong(Context context, String xmlName, String key, long value) {
        SharedPreferences settingPre = getShareModeWrite(context, xmlName);
        if (settingPre != null) {
            settingPre.edit().putLong(key, value).apply();
        }

    }

    public static String getString(Context context, String xmlName, String key, String defaultValue) {
        SharedPreferences settingPre = getShareModeRead(context, xmlName);
        return null == settingPre ? defaultValue : settingPre.getString(key, defaultValue);
    }

    public static void setString(Context context, String xmlName, String key, String value) {
        SharedPreferences settingPre = getShareModeWrite(context, xmlName);
        if (settingPre != null) {
            settingPre.edit().putString(key, value).apply();
        }
    }

    public static Float getFloat(Context context, String xmlName, String keyName, Float keyValue) {
        SharedPreferences settingPre = getShareModeRead(context, xmlName);
        return null == settingPre ? keyValue : settingPre.getFloat(keyName, keyValue);
    }

    public static void setFloat(Context context, String xmlName, String keyName, Float keyValue) {
        SharedPreferences settingPre = getShareModeWrite(context, xmlName);
        if (settingPre != null) {
            settingPre.edit().putFloat(keyName, keyValue).apply();
        }
    }

    /**
     * 获取字符串集合。已做客户端版本判断。
     *
     * @param context
     * @param xmlName   xml名称。
     * @param keyName   KEY.
     * @param defValues 缺省值。
     * @return 【不要修改返回的Set集合】Note that you must not modify the set instance returned by this call. The consistency of
     * the stored data is not guaranteed if you do, nor is your ability to modify the instance at all.
     * @see SharedPreferences#getStringSet(String, Set)
     * @since 2014年12月8日
     */
    @SuppressLint({"NewApi", "InlinedApi"})
    public static Set<String> getStringSet(Context context, String xmlName, String keyName, Set<String> defValues) {
        SharedPreferences settingPre;
        if (context == null) {
            return defValues;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            settingPre = context.getSharedPreferences(xmlName, Context.MODE_MULTI_PROCESS);
            return settingPre.getStringSet(keyName, defValues);
        } else {
            settingPre = context.getSharedPreferences(xmlName, Context.MODE_WORLD_READABLE);
            String values = settingPre.getString(keyName, null);
            if (!TextUtils.isEmpty(values)) {
                if (defValues == null) {
                    defValues = new HashSet<>();
                }
                defValues.clear();
                String[] strs = values.split(STRING_SPLITER);
                Collections.addAll(defValues, strs);
            }
            return defValues;
        }
    }

    /**
     * 存储字符串集合。已做客户端版本判断。<br>
     * <p>Note that you must not modify the set instance returned by this call.<br>
     * The consistency of the stored data is not guaranteed if you do, nor is your ability to modify the instance at
     * all.</p>
     * 不能更新的问题就出在getStringSet的object和putStringSet的object不能是同一个，不能在get之后，进行更改，然后又put进去，这样是无法更改的。
     *
     * @param context
     * @param xmlName xml名称。
     * @param keyName KEY.
     * @param values  集合的值。
     * @since 2014年12月8日
     */
    @SuppressLint({"NewApi", "InlinedApi"})
    public static void setStringSet(Context context, String xmlName, String keyName, Set<String> values) {
        SharedPreferences settingPre;
        if (context == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            settingPre = context.getSharedPreferences(xmlName, Context.MODE_MULTI_PROCESS);
            Editor edit = settingPre.edit();
            edit.clear();
            edit.putStringSet(keyName, values).apply();
        } else {
            settingPre = context.getSharedPreferences(xmlName, Context.MODE_WORLD_WRITEABLE);
            // 低版本系统转换为String存储
            StringBuilder builder = new StringBuilder();
            if (values != null && !values.isEmpty()) {
                for (String v : values) {
                    builder.append(v).append(STRING_SPLITER);
                }
            }
            Editor edit = settingPre.edit();
            edit.putString(keyName, builder.toString()).apply();
        }
    }

    /**
     * 删除一条记录。
     *
     * @param context
     * @param xmlName
     * @param keyName
     * @since 2014年12月6日
     */
    public static void remove(Context context, String xmlName, String keyName) {
        SharedPreferences settingPre = getShareModeWrite(context, xmlName);
        if (settingPre != null) {
            settingPre.edit().remove(keyName).apply();
        }
    }

    /**
     * 删除SharePreference创建的Xml文件。
     *
     * @param context
     * @param xmlName
     * @since 2014年12月6日
     */

    public static void deleteXML(Context context, String xmlName) {
        SharedPreferences settingPre = getShareModeWrite(context, xmlName);
        if (settingPre != null) {
            settingPre.edit().clear().apply();
        }
    }

    /**
     * 用于设置key的名称和默认值,key值运用泛型
     */
    public static class KEY <T> {
        public final String key;// 键的名称
        public final T defaultValue;// 键的默认值

        public KEY(String key, T defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }
    }

    /**
     * 从SharePreference中直接获取一个Map.
     *
     * @param context
     * @param xmlName
     * @return Map
     * @since 2015年2月9日
     */
    public static Map<String, ?> getAll(Context context, String xmlName) {
        SharedPreferences settingPre = getShareModeRead(context, xmlName);
        if (settingPre == null) {
            return null;
        }
        return settingPre.getAll();

    }

    /**
     * 将一个Map存入SharePreference。
     *
     * @param context
     * @param xmlName
     * @param map
     * @since 2015年2月9日
     */
    public static void putAll(Context context, String xmlName, Map<String, String> map) {
        if (context == null || map == null) {
            return;
        }
        SharedPreferences settingPre;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            settingPre = context.getSharedPreferences(xmlName, Context.MODE_MULTI_PROCESS);
        } else {
            settingPre = context.getSharedPreferences(xmlName, Context.MODE_WORLD_READABLE);
        }
        Editor edit = settingPre.edit();
        for (Entry<String, String> entry : map.entrySet()) {
            if (entry != null && !TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                edit.putString(entry.getKey(), entry.getValue());
            }
        }
        edit.apply();
    }

    private static SharedPreferences getShareModeRead(Context context, String xmlName) {
        if (context != null) {
            SharedPreferences settingPre;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                settingPre = context.getSharedPreferences(xmlName, Context.MODE_MULTI_PROCESS);
            } else {
                settingPre = context.getSharedPreferences(xmlName, Context.MODE_WORLD_READABLE);
            }
            return settingPre;
        }
        return null;
    }

    private static SharedPreferences getShareModeWrite(Context context, String xmlName) {
        if (context != null) {
            SharedPreferences settingPre;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                settingPre = context.getSharedPreferences(xmlName, Context.MODE_MULTI_PROCESS);
            } else {
                settingPre = context.getSharedPreferences(xmlName, Context.MODE_WORLD_WRITEABLE);
            }
            return settingPre;
        }
        return null;
    }

}
