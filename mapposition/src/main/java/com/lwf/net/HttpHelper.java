package com.lwf.net;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lwf.base.Constant;
import com.lwf.base.MyApplication;
import com.lwf.util.AppUtils;
import com.lwf.util.DeviceUtil;
import com.lwf.util.Installation;
import com.lwf.share.LogUtils;
import com.lwf.util.NetUtils;
import com.lwf.util.SignUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装HTTP请求，返回JSON数据
 *
 * @author liuweiping
 * @since 16/7/24
 */
public class HttpHelper {

    /**
     * POST请求方式
     *
     * @param context
     * @param url      可以带有域名开头，如果 <code>/</code> 开头会自动使用域名 {@value ApiUrls#API_HOST}
     * @param params
     * @param callback 对于JSON结果，可以使用 {@link com.zhy.http.okhttp.callback.StringCallback}
     */
    public static void post(Context context, @NonNull String url, @Nullable Map<String, String> params, @Nullable Callback<?> callback) {
        if (context == null)
            context = MyApplication.getInstance();
        if (params == null) {
            params = new HashMap<>();
        }
        Map<String, String> sendParams = dealCommonParams(context, params);
        String sign = SignUtils.signByMd5(sendParams, Constant.SIGN_MD5_KEY_STRING);
        sendParams.put("sign", sign);
        url = compatUrl(url);
        LogUtils.i(url);
        LogUtils.i(params.toString());
        OkHttpUtils.post().url(url).params(sendParams).build().execute(callback);
    }

    /**
     * @param context
     * @param url
     * @param params
     * @param files    上传文件
     * @param callback
     */
    public static void post(Context context, @NonNull String url, @Nullable Map<String, String> params, @NonNull String fileKey,
                            @Nullable Map<String, File> files, @Nullable Callback<?> callback) {
        if (context == null)
            context = MyApplication.getInstance();
        if (params == null) {
            params = new HashMap<>();
        }
        Map<String, String> sendParams = dealCommonParams(context, params);
        String sign = SignUtils.signByMd5(sendParams, Constant.SIGN_MD5_KEY_STRING);
        sendParams.put("sign", sign);
        url = compatUrl(url);
        LogUtils.i(url);
        LogUtils.i(params.toString());
        PostFormBuilder builder = OkHttpUtils.post().url(url).params(sendParams);
        if (files != null) {
            builder.files(fileKey, files);
            LogUtils.i(fileKey + " : " + files.toString());
        }
        builder.build().execute(callback);
    }

    @NonNull
    private static String compatUrl(@NonNull String url) {
        if (url.startsWith("/"))
            url = ApiUrls.API_HOST + url;
        return url;
    }

    /**
     * GET请求方式
     *
     * @param context
     * @param url      可以带有域名开头，如果 <code>/</code> 开头会自动使用域名 {@value ApiUrls#API_HOST}
     * @param params
     * @param callback 对于JSON结果，可以使用 {@link com.zhy.http.okhttp.callback.StringCallback}
     */
    public static void get(@NonNull Context context, @NonNull String url, @Nullable Map<String, String> params,
                           @Nullable Callback<?> callback) {
        if (params == null) {
            params = new HashMap<>();
        }
        Map<String, String> sendParams = dealCommonParams(context, params);
        String sign = SignUtils.signByMd5(sendParams, Constant.SIGN_MD5_KEY_STRING);
        sendParams.put("sign", sign);
        url = compatUrl(url);
        LogUtils.i(url);
        LogUtils.i(params.toString());
        OkHttpUtils.get().url(url).params(sendParams).build().execute(callback);
    }

    public static void download(@NonNull Context context, @NonNull String url, @Nullable FileCallBack callback) {
        OkHttpUtils.get().tag(url).url(url).build().execute(callback);
    }

    public static void stop(@NonNull String url) {
        OkHttpUtils.getInstance().cancelTag(url);
    }

    /**
     * 设置公共参数
     * <p/>
     * 说明：
     * ts[必选]: 时间戳
     * sign[必选]: 签名
     * sid[必选]: sessionid
     * uid: 当前登录用用户ID
     *
     * @param context
     * @param params
     */
    private static Map<String, String> dealCommonParams(Context context, @NonNull Map<String, String> params) {
//        UserInfo userInfo = UserManager.getInstance().getUserInfo();
//
//        params.put("ts", String.valueOf(System.currentTimeMillis()));
//
//        if (!params.containsKey("sid"))
//            if (userInfo != null && userInfo.session != null && userInfo.session.sid != null) {
//                params.put("sid", userInfo.session.sid);
//            } else {
//                params.put("sid", "");
//            }
//        if (!params.containsKey("campus_id"))
//            params.put("campus_id", String.valueOf(UserManager.getInstance().getCampusId()));
//        if (!params.containsKey("uid"))
//            if (userInfo != null && userInfo.id != 0) {
//                params.put("uid", String.valueOf(userInfo.id));
//            } else {
//                params.put("uid", "0");//保证至少有个uid，未登录时会需要传这个参数
//            }
        //p1 ~ p10 固定参数
        //p1 : 机型
        //p2 : 友盟渠道
        //p3 : versionCode
        //p4 : versionName
        //p5 : "网络类型: 0:没定义 4:WIFI 3:3G 2:2G 1:WAP"
        //p6 : 唯一设备标识号
        //p7 : screenWidth*screenHeight
        //p8 : imei
        //p9 : CURR_LEVE_SDK_INIT
        //p10 : android版本(类似: 2.3.4)
        params.put("p1", Build.MODEL);
        params.put("p2", AnalyticsConfig.getChannel(context));
        params.put("p3", String.valueOf(AppUtils.getVersionCode(context)));
        params.put("p4", AppUtils.getVersionName(context));
        params.put("p5", String.valueOf(NetUtils.getNetWorkType(context)));
        params.put("p6", Installation.id(context));
        params.put("p7", DeviceUtil.getScreenSize(context));
        params.put("p8", DeviceUtil.getIMEI(context));
        params.put("p9", String.valueOf(Build.VERSION.SDK_INT));
        params.put("p10", Build.VERSION.RELEASE);
        return params;
    }

}
