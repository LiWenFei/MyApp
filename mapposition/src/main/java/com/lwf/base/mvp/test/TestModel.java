package com.lwf.base.mvp.test;

/**
 * Created by liwenfei on 2016/10/11.
 */

public class TestModel implements ITestContract.ITestModel {

    private ITestContract.ResultListener resultListener;

    public TestModel(ITestContract.ResultListener resultListener) {
        this.resultListener = resultListener;
    }

    @Override
    public void Login() {
        onSuccess();
    }

    public void onSuccess() {
        if (resultListener != null) {
            resultListener.onsuccess();
        }
    }
}
