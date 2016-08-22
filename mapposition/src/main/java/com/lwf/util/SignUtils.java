package com.lwf.util;

import android.text.TextUtils;

import com.lwf.share.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author luoch
 * @Description: HTTP
 * @date 2014-11-27 下午4:14:16
 */
public class SignUtils {

    private static final String TAG = "SignUtils";

    public static String signByMd5(Map<String, String> params, String secret) {
        List<String> list = new ArrayList<>(params.keySet());
        // 对key键值按字典升序排序
        Collections.sort(list);
        // 加密串
        StringBuilder sb = new StringBuilder("");

        for (String key : list) {
            String value = params.get(key);
            if (!TextUtils.isEmpty(value)) {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        sb.append("secret=").append(secret);
        LogUtils.d("signStr:   " + sb.toString());
        return MD5Util.getMD5Code(sb.toString());
    }
}
