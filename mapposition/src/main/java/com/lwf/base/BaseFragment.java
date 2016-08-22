package com.lwf.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lwf.mapposition.R;
import com.lwf.util.AbViewUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * @since 16/7/24
 */
public class BaseFragment extends Fragment implements IShowToast, IFragmentVisible {
    /**
     * 类名，统计用名
     */
    public String TAG = this.getClass().getSimpleName();
    /**
     * 在Fragment中可以使用此Activity对象作为Context使用。<br>
     * <b>注意：禁止使用Fragment的gerRecourse()、getString()等方法，<font color=red>一律使用Activity中的相关资源获取方法</font>。</b>
     */
    protected BaseFragmentActivity mActivity;
    /**
     * 是否可见
     */
    protected boolean isVisible = false;
    /**
     * {@link Fragment#onCreate(Bundle)} 是否已经调用。
     */
    protected boolean isCreated;

    /**
     * 使用 {@link AbViewUtil} 处理
     *
     * @param inflater
     * @param container
     * @param layoutId
     * @return
     */
    protected View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, int layoutId) {
        View view = inflater.inflate(layoutId, container, false);
        if (view instanceof ViewGroup)
            AbViewUtil.scaleContentView((ViewGroup)view);
        else
            AbViewUtil.scaleView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.isCreated = true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // //No call for super(). Bug on API Level > 11.
        if (Build.VERSION.SDK_INT <= 11) {
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onAttach(Context context) {
        this.mActivity = (BaseFragmentActivity)context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }

    /**
     * 此方法目前仅适用于标示ViewPager中的Fragment是否真实可见<br>
     * For 友盟统计的页面线性不交叉统计需求
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 仅onCreate后才统计
        if (isCreated) {
            if (isVisibleToUser) {
                onVisible();
            } else {
                onInvisible();
            }
        }
    }

    @Override
    public void onVisible() {
        if (isVisible) {
            return;
        }
//        LogUtils.d(TAG);
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(mActivity);
        isVisible = true;
    }

    @Override
    public void onInvisible() {
        if (!isVisible) {
            return;
        }
//        LogUtils.d(TAG);
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(mActivity);
        isVisible = false;
    }

    /**
     * Toast封装,防止短时间内多次调用toast导致不停弹出的问题
     *
     * @param msgId 要显示的字符串的资源id
     * @see Toast
     */
    @Override
    public void showMsg(int msgId) {
        if (mActivity != null) {
            mActivity.showMsg(msgId);
        }
    }

    /**
     * Toast封装,防止短时间内多次调用toast导致不停弹出的问题
     *
     * @param msgStr 要显示的字符串
     * @see Toast
     */
    @Override
    public void showMsg(CharSequence msgStr) {
        if (mActivity != null) {
            mActivity.showMsg(msgStr);
        }
    }

    /**
     * Toast封装,防止短时间内多次调用toast导致不停弹出的问题
     *
     * @param duration Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     * @see Toast
     */
    @Override
    public void showMsg(int msgId, int duration) {
        if (mActivity != null) {
            mActivity.showMsg(msgId, duration);
        }
    }

    /**
     * Toast封装,防止短时间内多次调用toast导致不停弹出的问题
     *
     * @param msgStr   要显示的字符串
     * @param duration Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     * @see Toast
     */
    @Override
    public void showMsg(CharSequence msgStr, int duration) {
        if (mActivity != null) {
            mActivity.showMsg(msgStr, duration);
        }
    }

    /**
     * @return 子类实现后，可以为Fragment命名
     */
    public CharSequence getTitle() {
        Context context = mActivity;
        if (context == null)
            context = MyApplication.getInstance();
        return context.getString(R.string.app_name);
    }
}
