package com.lwf.base.mvp.test;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lwf.base.BaseFragmentActivity;
import com.lwf.mapposition.R;

/**
 * Created by liwenfei on 2016/10/11.
 */

public class TestActivity extends BaseFragmentActivity implements ITestContract.ITestView{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_asd);

        TestFragment fragment = new TestFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contaner, fragment).commit();
    }

    @Override
    public void showLoginSuccess() {
//        LogUtils.i("success");
//        showMsg("success");
    }
}
