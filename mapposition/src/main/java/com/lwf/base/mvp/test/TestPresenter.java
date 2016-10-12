package com.lwf.base.mvp.test;

import com.lwf.base.mvp.BasePresenter;

/**
 * Created by liwenfei on 2016/10/11.
 */

public class TestPresenter extends BasePresenter<ITestContract.ITestView, ITestContract.ITestModel> implements ITestContract.ITestPresenter, ITestContract.ResultListener {

    public TestPresenter() {
        mModel = new TestModel(this);
    }

    @Override
    public void Login() {
        mModel.Login();
    }

    @Override
    public void onsuccess() {
        mView.showLoginSuccess();
    }
}
