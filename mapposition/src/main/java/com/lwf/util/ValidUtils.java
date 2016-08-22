package com.lwf.util;

import android.text.TextUtils;

/**
 * 各种验证
 *
 * @author liuweiping
 * @since 16/7/28
 */
public class ValidUtils {
    /**
     * 验证手机格式
     * <p>
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188 <br/>
     * 联通：130、131、132、152、155、156、185、186 <br/>
     * 电信：133、153、180、189、（1349卫通）<br/>
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9 <br/>
     */
    public static boolean isMobileNO(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            String telRegex = "[1]\\d{10}";
            return mobiles.matches(telRegex);
        }
    }

    public static boolean checkPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        } else {
            String telRegex = "([a-z]|[A-Z]|[0-9]){6,20}";
            return password.matches(telRegex);
        }
    }

    /**
     * 检查姓名
     *
     * @param name
     * @return
     */
    public static boolean isValidName(String name) {
//        return !TextUtils.isEmpty(name) && TextUtils.getTrimmedLength(name) < 10;
        if (TextUtils.isEmpty(name)) {
            return false;
        } else {
            String regex = "([a-z]|[A-Z]|[\\u4e00-\\u9fa5]){1,10}";
            return name.matches(regex);
        }
    }

    /**
     * 验证 短信验证码格式
     */
    public static boolean isValidSecurityCode(String code) {
        // 6位数字
        if (TextUtils.isEmpty(code)) {
            return false;
        } else {
            String telRegex = "\\d{6}";
            return code.matches(telRegex);
        }
    }
}
