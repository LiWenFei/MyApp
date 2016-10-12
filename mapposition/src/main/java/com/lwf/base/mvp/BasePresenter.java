package com.lwf.base.mvp;

/**
 * Created by liwenfei on 2016/10/11.
 */

public abstract class BasePresenter<T extends IView, M extends IModel> implements IPresenter<T> {

    protected static final String TAG = BasePresenter.class.getSimpleName();

    protected T mView;
    protected M mModel;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public T getmView() {
        return mView;
    }

    public M getmModel() {
        return mModel;
    }
}
