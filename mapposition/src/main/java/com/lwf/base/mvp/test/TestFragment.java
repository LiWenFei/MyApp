package com.lwf.base.mvp.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lwf.base.mvp.BaseMvpFragment;
import com.lwf.mapposition.R;
import com.lwf.share.LogUtils;

/**
 * Created by liwenfei on 2016/10/12.
 */

public class TestFragment extends BaseMvpFragment<TestPresenter> implements ITestContract.ITestView {

    private View rootView;

    @Override
    protected TestPresenter onLoadPresenter() {
        return new TestPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.act_text, null);
            getPresenter().Login();
        }
        return rootView;
    }

    @Override
    public void showLoginSuccess() {
        LogUtils.i("success");
        showMsg("success");
    }
}
