package com.lwf.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lwf.base.BaseFragmentActivity;

/**
 * Created by liwenfei on 2016/10/11.
 * 实现MVP模式的Acitivity基类
 */

public abstract class BaseMvpActivity<P extends IPresenter> extends BaseFragmentActivity implements IView {

    public static final String TAG = BaseMvpActivity.class.getSimpleName();

    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = onLoadPresenter();

        if (getPresenter() != null) {
            getPresenter().attachView(this);
        }

        initViews(savedInstanceState);
        initEventAndData();
    }

    protected P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        if (getPresenter() != null) {
            getPresenter().detachView();
        }
        super.onDestroy();
    }

    /**
     * 初始化Presenter
     *
     * @return
     */
    protected abstract P onLoadPresenter();

    /**
     * 初始化View
     *
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    protected abstract void initEventAndData();
}
