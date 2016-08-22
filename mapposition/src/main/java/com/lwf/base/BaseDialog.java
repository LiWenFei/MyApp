package com.lwf.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.lwf.util.AbViewUtil;

/**
 * Dialog的基类，所有的Dialog都需要继承此类，内部解决了Activity状态造成的崩溃问题。
 *
 * @since 2015年5月27日
 */
public class BaseDialog extends Dialog {

    private Context mContext;

    public BaseDialog(Context context) {
        super(context);
        mContext = context;
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = View.inflate(mContext, layoutResID, null);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        if (view instanceof ViewGroup)
            AbViewUtil.scaleContentView((ViewGroup)view);
        else
            AbViewUtil.scaleView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        if (view instanceof ViewGroup)
            AbViewUtil.scaleContentView((ViewGroup)view);
        else
            AbViewUtil.scaleView(view);
    }

    @Override
    public void show() {
        if (!isActivityValid(mContext)) {
            return;
        }

        try {
            super.show();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void dismiss() {
        if (!isActivityValid(mContext)) {
            return;
        }
        try {
            super.dismiss();
        } catch (Exception ignored) {
        }
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
}
