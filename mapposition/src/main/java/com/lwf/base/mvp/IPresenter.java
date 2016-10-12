package com.lwf.base.mvp;

/**
 * Created by liwenfei on 2016/10/11.
 * Presenter 接口
 */

public interface IPresenter<T extends IView> {
    /**
     * 加载IView接口
     * @param view
     */
    void attachView(T view);

    /**
     * 注销IView接口
     */
    void detachView();
}
