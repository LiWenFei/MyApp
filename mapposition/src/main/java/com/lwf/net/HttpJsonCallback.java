package com.lwf.net;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lwf.base.ActivityInstanceRef;
import com.lwf.base.MyApplication;
import com.lwf.mapposition.R;
import com.lwf.util.AppUtils;
import com.lwf.share.LogUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * @author liuweiping
 * @since 16/8/4
 */
public abstract class HttpJsonCallback<T> extends StringCallback {
    private TypeToken<HttpResult<T>> type;
    private String response;

    /**
     * @param type 例如 {@code new TypeToken<HttpResult<UserInfo>>() {} }
     */
    public HttpJsonCallback(TypeToken<HttpResult<T>> type) {
        this.type = type;
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        LogUtils.e(e);
        onFailed(MyApplication.getInstance().getString(R.string.request_failed), id);
    }

    @Override
    public void onResponse(String response, int id) {
        this.response = response;
        LogUtils.i(response);
        // 解析json
        HttpResult<T> result = new Gson().fromJson(response, type.getType());
        if (result == null) {
            onFailed(MyApplication.getInstance().getString(R.string.request_failed), id);
            return;
        }
        switch (result.getCode()) {
            case 0:
                onSuccess(result.getBody(), id);
                break;
            case 61171:
//                UserManager.getInstance().logout();
                // 打开登录页
                Activity activity = ActivityInstanceRef.getCurActivity();
//                if (activity != null && activity instanceof BaseFragmentActivity)
//                    UserManager.getInstance().openLogin((BaseFragmentActivity)activity);
//                else
                    AppUtils.showToast(MyApplication.getInstance(), result.getMsg());
            default:
                onFailed(result.getMsg(), id);
                break;
        }
    }

    /**
     * 请求失败，或者结果状态为失败
     *
     * @param msg
     * @param id
     */
    public abstract void onFailed(String msg, int id);

    /**
     * 请求成功，获取期望的结果
     *
     * @param bean 结果对象
     * @param id
     */
    public abstract void onSuccess(T bean, int id);

    /**
     * @return 获取原始JSONO数据
     */
    public String getResponse() {
        return response;
    }
}
