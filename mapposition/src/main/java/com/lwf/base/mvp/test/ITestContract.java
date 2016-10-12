package com.lwf.base.mvp.test;

import com.lwf.base.mvp.IModel;
import com.lwf.base.mvp.IPresenter;
import com.lwf.base.mvp.IView;

/**
 * Created by liwenfei on 2016/10/11.
 */

public interface ITestContract {

    interface ITestView extends IView {
        void showLoginSuccess();

    }

    interface ITestModel extends IModel {
        void Login();

    }

    interface ITestPresenter extends IPresenter<ITestView> {
        void Login();
    }

    interface ResultListener {
        void onsuccess();
    }
}
