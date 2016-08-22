package com.lwf.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lwf.mapposition.R;
import com.lwf.util.AbViewUtil;
import com.lwf.util.AppUtils;
import com.lwf.view.MyToast;

/**
 * 带有Fragment的Activity
 * <p/>
 * HomeActivity
 *
 * @author liuweiping
 * @since 16/8/19
 */
public class BaseFragmentActivity extends AppCompatActivity implements IShowToast {

    @Override
    protected void onResume() {
        super.onResume();
        // 有Fragment的Activity，不统计页面
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 有Fragment的Activity，不统计页面
    }

    /**
     * 类名，统计用名
     */
    protected String TAG = this.getClass().getSimpleName();
    private MyToast mToast;

    /**
     * @return 是否在退出页面时打开首页
     */
    protected boolean isBack2Home() {
        return true;
    }

    @Override
    protected void onDestroy() {
        // 当Activity销毁时，从记录中清除
        ActivityInstanceRef.clearCurActivity(this);
        super.onDestroy();

    }

    @Override
    public void finish() {
//        // 退出页面时，始终把首页作为最后一页
//        if (!(this instanceof HomeActivity) && isBack2Home()) {
//            try {
//                if (ActivityInstanceRef.getActivityCount() <= 1) {
//                    // 仅有当前页面，打开首页
//                    startActivity(new Intent(this, HomeActivity.class));
//                }
//            } catch (Exception e) {
//                LogUtils.e(e);
//            }
//        }
        super.finish();
    }

    /**
     * 使用 {@link AbViewUtil} 处理
     *
     * @param layoutResID
     * @see android.app.Activity#setContentView(int)
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View root = inflater.inflate(layoutResID, null);
        setContentView(root);
    }

    /**
     * 使用 {@link AbViewUtil} 处理
     *
     * @param view
     * @see android.app.Activity#setContentView(View)
     */
    @Override
    public void setContentView(@NonNull View view) {
        super.setContentView(view);
        if (view instanceof ViewGroup)
            AbViewUtil.scaleContentView((ViewGroup)view);
        else
            AbViewUtil.scaleView(view);
    }

    /**
     * 使用 {@link AbViewUtil} 处理
     *
     * @param view
     * @param params
     * @see android.app.Activity#setContentView(View, ViewGroup.LayoutParams)
     */
    @Override
    public void setContentView(@NonNull View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        if (view instanceof ViewGroup)
            AbViewUtil.scaleContentView((ViewGroup)view);
        else
            AbViewUtil.scaleView(view);
    }

    /**
     * 使用 {@link AbViewUtil} 处理
     *
     * @param view
     * @param params
     * @see android.app.Activity#addContentView(View, ViewGroup.LayoutParams)
     */
    @Override
    public void addContentView(@NonNull View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
        if (view instanceof ViewGroup)
            AbViewUtil.scaleContentView((ViewGroup)view);
        else
            AbViewUtil.scaleView(view);
    }

    /**
     * Toast封装,防止短时间内多次调用toast导致不停弹出的问题
     *
     * @param msgId 要显示的字符串的资源id
     * @see Toast
     */
    @Override
    public void showMsg(int msgId) {
        showMsg(getString(msgId));
    }

    /**
     * Toast封装,防止短时间内多次调用toast导致不停弹出的问题
     *
     * @param msgStr 要显示的字符串
     * @see Toast
     */
    @Override
    public synchronized void showMsg(CharSequence msgStr) {
        showMsg(msgStr, Toast.LENGTH_SHORT);
    }

    /**
     * Toast封装,防止短时间内多次调用toast导致不停弹出的问题
     *
     * @param msgId    要显示的字符串的资源id
     * @param duration Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     * @see Toast
     */
    @Override
    public void showMsg(int msgId, int duration) {
        showMsg(getString(msgId), duration);
    }

    /**
     * Toast封装,防止短时间内多次调用toast导致不停弹出的问题
     *
     * @param msgStr   要显示的字符串
     * @param duration Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     * @see Toast
     */
    @Override
    public synchronized void showMsg(CharSequence msgStr, int duration) {
        if (mToast == null) {
            mToast = new MyToast(this, msgStr, duration);
        } else {
            mToast.msgStr = msgStr;
            mToast.duration = duration;
        }
        runOnUiThread(mToast);
    }

    /**
     * <i>对于API 23以下版本，直接返回true. <i/><br/>
     * <p/>
     * 检查和请求权限.
     *
     * @param permissions 需要请求的权限数组
     * @return true - 不需要请求权限；false - 需要请求权限，应该阻止程序继续运行
     */
    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkPermissionsForM(@NonNull String... permissions) {
        return AppUtils.checkPermissionsForM(this, 0, permissions);
    }

    /**
     * <i>对于API 23以下版本，直接返回true. <i/><br/>
     * <p/>
     * 检查和请求权限.
     *
     * @param requestCode
     * @param permissions 需要请求的权限数组
     * @return true - 不需要请求权限；false - 需要请求权限，应该阻止程序继续运行
     */
    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkPermissionsForM(int requestCode, @NonNull String... permissions) {
        return AppUtils.checkPermissionsForM(this, requestCode, permissions);
    }

    /**
     * 自定义Action的建造者
     */
    public class ActionBarBuilder {
        @LayoutRes
        private int layoutId;
        @IdRes
        private int titleViewId;
        @StringRes
        private int titleStringId = R.string.app_name;
        @IdRes
        private int rightViewId;
        @DrawableRes
        private int rightDrawableId = R.mipmap.icon_add;
        @IdRes
        private int backViewId;
        @DrawableRes
        private int backDrawableId = R.mipmap.icon_back;

        private View.OnClickListener backClickListener;
        private View.OnClickListener rightViewClickListener;

        /**
         * 最终构造并显示ActionBar
         *
         * @return 自定义布局的View。此建造者没有定义的控件，可以继续使用此View进行配置。
         */
        public View build() {
            View view = null;
//      当当前的theme不是NoTitleBar的时候
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
//          显示当前的actionBar
                actionBar.show();
//          显示home区域
                actionBar.setDisplayShowHomeEnabled(false);
                actionBar.setDisplayShowCustomEnabled(true);
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue_action_bar)));
//          设置填充的参数
                // 默认值
                if (layoutId == 0) {
                    setupDefaultLayout();
                }
                LayoutInflater inflate = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflate.inflate(layoutId, null);
//          使用abViewUtils进行屏幕的适配
                AbViewUtil.scaleContentView((ViewGroup)view);

                // 填充
                if (titleViewId != 0) {
                    View titleTV = view.findViewById(titleViewId);
                    if (titleStringId == 0)
                        titleTV.setVisibility(View.GONE);
                    else {
                        if (titleTV instanceof TextView)
                            ((TextView)titleTV).setText(titleStringId);
                        titleTV.setVisibility(View.VISIBLE);
                    }
                }

                if (rightViewId != 0) {
                    View iconIV = view.findViewById(rightViewId);
                    if (rightDrawableId == 0)
                        iconIV.setVisibility(View.GONE);
                    else {
                        if (iconIV instanceof ImageView)
                            ((ImageView)iconIV).setImageResource(rightDrawableId);
                        iconIV.setOnClickListener(rightViewClickListener);
                        iconIV.setVisibility(View.VISIBLE);
                    }
                }

                if (backViewId != 0) {
                    View backIV = view.findViewById(backViewId);
                    if (backDrawableId == 0)
                        backIV.setVisibility(View.GONE);
                    else {
                        if (backIV instanceof ImageView)
                            ((ImageView)backIV).setImageResource(backDrawableId);
                        backIV.setVisibility(View.VISIBLE);
                        if (backClickListener == null)
                            backClickListener = new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            };
                        backIV.setOnClickListener(backClickListener);
                    }
                }

                android.support.v7.app.ActionBar.LayoutParams params = new android.support.v7.app.ActionBar.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                actionBar.setCustomView(view, params);
            }
            return view;
        }

        /**
         * 设置默认的Layout，默认Layout的各控件ID，设置后退默认点击监听。
         */
        private void setupDefaultLayout() {
            this.setLayoutId(R.layout.action_bar_default).setTitleViewId(R.id.actionbar_title_tv)
                .setRightViewId(R.id.actionbar_right_icon_iv).setBackViewId(R.id.actionbar_back_iv);
        }

        /**
         * @param backClickListener 后退事件的监听。如果不设置，默认为关闭Activity。默认点击View的ID = R.id.actionbar_back_iv
         * @return ActionBarBuilder
         */
        public ActionBarBuilder setBackClickListener(View.OnClickListener backClickListener) {
            this.backClickListener = backClickListener;
            return this;
        }

        /**
         * @param rightViewClickListener 右侧按钮的点击监听。默认点击View的ID = R.id.actionbar_right_icon_iv
         * @return
         */
        public ActionBarBuilder setRightViewClickListener(View.OnClickListener rightViewClickListener) {
            this.rightViewClickListener = rightViewClickListener;
            return this;
        }

        /**
         * @param layoutId 自定义布局的ID。如果不设置可以使用默认布局，而且各控件的ID设置无效；如果设置了自定义布局，则必须设置各控件ID。
         * @return
         */
        public ActionBarBuilder setLayoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        /**
         * @param titleViewId 标题TextView的ID
         * @return
         */
        public ActionBarBuilder setTitleViewId(int titleViewId) {
            this.titleViewId = titleViewId;
            return this;
        }

        /**
         * @param titleStringId 标题文字ID。设置为0，隐藏标题部分。
         * @return
         */
        public ActionBarBuilder setTitleStringId(int titleStringId) {
            this.titleStringId = titleStringId;
            return this;
        }

        /**
         * @param rightViewId 设置右侧按钮的ID
         * @return
         */
        public ActionBarBuilder setRightViewId(int rightViewId) {
            this.rightViewId = rightViewId;
            return this;
        }

        /**
         * @param rightDrawableId 设置右侧图片ID。设置为0，隐藏右侧按钮。
         * @return
         */
        public ActionBarBuilder setRightDrawableId(int rightDrawableId) {
            this.rightDrawableId = rightDrawableId;
            return this;
        }

        /**
         * @param backViewId 设置后退按钮的ID
         * @return
         */
        public ActionBarBuilder setBackViewId(int backViewId) {
            this.backViewId = backViewId;
            return this;
        }

        /**
         * @param backDrawableId 设置后退按钮的图片ID。设置为0，隐藏后退按钮。
         * @return
         */
        public ActionBarBuilder setBackDrawableId(int backDrawableId) {
            this.backDrawableId = backDrawableId;
            return this;
        }
    }
}
