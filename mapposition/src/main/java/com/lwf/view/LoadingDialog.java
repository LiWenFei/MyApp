package com.lwf.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lwf.base.BaseDialog;
import com.lwf.mapposition.R;

/**
 * @author liuweiping
 * @since 2015年6月17日
 */
public class LoadingDialog extends BaseDialog {
    //    private ImageView iconIV;
    private ProgressBar bar;
    private TextView contentTV;
    private String content;

    public LoadingDialog(Context context) {
        super(context, R.style.Dialog);
    }

    public void setContent(String content) {
        this.content = content;
        this.contentTV.setText(content);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        bar = (ProgressBar)findViewById(R.id.loading_img);
        contentTV = (TextView)findViewById(R.id.loading_content);
        if (!TextUtils.isEmpty(this.content)) {
            this.contentTV.setText(content);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            AnimationDrawable animationDrawable = (AnimationDrawable)iconIV.getBackground();
//            animationDrawable.start();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
