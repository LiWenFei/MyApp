package com.lwf.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.EnvironmentCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.lwf.base.Constant;
import com.lwf.base.IShowToast;
import com.lwf.base.MyApplication;
import com.lwf.mapposition.R;
import com.lwf.share.LogUtils;
import com.lwf.view.LoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @date 16/7/20 18:17
 * @Description:
 */
public class AppUtils {

    private static PackageInfo getPackageInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            return pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        return packageInfo == null ? 0 : packageInfo.versionCode;
    }

    /**
     * 手机型号
     */

    public static String getMobile() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用的存储地址
     *
     * @param context Context
     * @return 应用的存储地址
     */
    public static File getMainPath(Context context) {
        File path;
        String state = EnvironmentCompat.getStorageState(Environment.getExternalStorageDirectory());
        if (!TextUtils.equals(state, Environment.MEDIA_MOUNTED)) {
            // 外部存储无效，获取内部存储
            path = new File(context.getFilesDir(), Constant.MAIN_PATH_DIR);
        } else {
            path = new File(Environment.getExternalStorageDirectory(), Constant.MAIN_PATH_DIR);
            try {
                if (!path.exists())
                    path.mkdirs();
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }

        return path;
    }

    /**
     * @param context
     * @return 下载APK的目录
     */
    public static File getApkPath(Context context) {
        File apkFile = new File(getMainPath(context).getAbsolutePath() + "/apk");
        try {
            if (!apkFile.exists())
                apkFile.mkdirs();
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return apkFile;
    }

    /**
     * 底部弹出PopupWindow.
     *
     * @param activity
     * @param contentView 内容布局。
     * @param parent      a parent view to get the {@link android.view.View#getWindowToken()} token from
     * @param pop         可复用的PopupWindow对象。
     * @return
     * @since 2015年6月19日
     */
    @SuppressWarnings("deprecation")
    public static PopupWindow showBottomPopup(final Activity activity, final View contentView, final View parent, PopupWindow pop) {
        if (activity == null || contentView == null || parent == null) {
            return null;
        }
        if (pop == null) {
            pop = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        }
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pop.setContentView(contentView);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.AnimBottom);
        pop.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);
        setBackgroundAlpha(activity.getWindow(), 0.6f);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                setBackgroundAlpha(activity.getWindow(), 1);
            }
        });

        return pop;
    }

    /**
     * 设置Activity的窗口透明度
     *
     * @param window {@link Window} ，如 Activity.getWindow()
     * @param alpha  透明度。1f - 无遮挡； 0f - 全黑 ； 0f~1f - 窗口变暗
     * @since 2015年6月18日
     */
    public static void setBackgroundAlpha(Window window, float alpha) {
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = alpha;
        window.setAttributes(attributes);
    }

    /**
     * 一个Intent的Activity是否可以打开
     *
     * @param context
     * @param intent
     * @return
     */
    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * 判断Context是否可以显示Dialog。
     *
     * @param context Context
     * @return Context是否可用
     * @since 2015年6月8日
     */
    public static boolean isActivityValid(Context context) {

        if (null != context && context instanceof Activity) {
            Activity at = (Activity)context;
            return !at.isFinishing();
        }
        return false;
    }

    /**
     * 显示Loading
     *
     * @param context          Context
     * @param cancelable       是否可取消
     * @param mLoadingDialog   复用的LoadingDialog
     * @param onCancelListener 取消事件的监听
     * @return LoadingDialog
     * @since 2015年5月28日
     */
    @SuppressLint("InflateParams")
    public static LoadingDialog showLoading(Context context, boolean cancelable, LoadingDialog mLoadingDialog,
                                            DialogInterface.OnCancelListener onCancelListener) {
        if (context != null) {

            if (!isActivityValid(context)) {
                return mLoadingDialog;
            }
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(context);
            }
            mLoadingDialog.setCancelable(cancelable);
            mLoadingDialog.setOnCancelListener(onCancelListener);
            mLoadingDialog.show();
            return mLoadingDialog;
        }
        return null;
    }

    /**
     * 关闭Loading
     *
     * @param loadingDialog 关闭的目标
     * @since 2015年5月28日
     */
    public static void dismissLoading(LoadingDialog loadingDialog) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param msg
     * @since 2015年6月1日
     */
    public static void showToast(Context context, int msg) {
        if (context == null) {
            context = MyApplication.getInstance();
        }
        if (context instanceof IShowToast)
            ((IShowToast)context).showMsg(msg);
        else
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param msg
     * @param duration How long to display the message.  Either {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
     * @since 2015年6月1日
     */
    public static void showToast(Context context, int msg, int duration) {
        if (context == null) {
            context = MyApplication.getInstance();
        }
        if (context instanceof IShowToast)
            ((IShowToast)context).showMsg(msg);
        else
            Toast.makeText(context, msg, duration).show();
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param msg
     * @since 2015年6月3日
     */
    public static void showToast(Context context, String msg) {
        if (context == null) {
            context = MyApplication.getInstance();
        }
        if (context instanceof IShowToast)
            ((IShowToast)context).showMsg(msg);
        else
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * <i>对于API 23以下版本，直接返回true. <i/><br/>
     * <p>
     * 检查和请求权限.
     *
     * @param activity    Activity
     * @param permissions 需要请求的权限数组
     * @return true - 不需要请求权限；false - 需要请求权限，应该阻止程序继续运行
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkPermissionsForM(Activity activity, int requestCode, @NonNull String... permissions) {
        // 添加权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> list = new ArrayList<>();
            for (String p : permissions) {
                int checkResult = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
                if (checkResult != PackageManager.PERMISSION_GRANTED)
                    list.add(p);
            }
            // 请求权限
            if (!list.isEmpty()) {
                activity.requestPermissions(list.toArray(new String[list.size()]), requestCode);
                return false;
            }
        }
        return true;
    }

    /**
     * 普通安装模式。
     *
     * @param ac
     * @param path
     * @param filename
     * @since 2015年5月26日
     */
    public static void installFileNormal(Context ac, String path, String filename) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.putExtra(path, filename);
            intent.setDataAndType(Uri.parse("file://" + path + "/" + filename), "application/vnd.android.package-archive");
            if (ac instanceof Activity) {
                ((Activity)ac).startActivityForResult(intent, 125);
            } else {
                ac.startActivity(intent);
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }

    }

}

