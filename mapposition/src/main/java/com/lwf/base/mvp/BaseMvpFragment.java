package com.lwf.base.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lwf.base.BaseActivity;
import com.lwf.base.BaseFragment;

/**
 * Created by liwenfei on 2016/10/11.
 */

public abstract class BaseMvpFragment<P extends IPresenter> extends BaseFragment implements IView {

    public static final String TAG = BaseMvpFragment.class.getSimpleName();

    private P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = onLoadPresenter();

        if (getPresenter() != null) {
            getPresenter().attachView(this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {

        }
    }

    protected P getPresenter() {
        return mPresenter;
    }

    @Override
    public void onDestroy() {
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
}
