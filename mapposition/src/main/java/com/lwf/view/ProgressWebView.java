package com.lwf.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;

import com.lwf.mapposition.R;
import com.lwf.share.LogUtils;
import com.lwf.view.jsbridge.BridgeWebView;

/**
 * @des 带进度条的 WebView
 */
public class ProgressWebView extends BridgeWebView {
    private static final String TAG = "ProgressWebView";

    private ProgressBar progressBar;

    private String mWebTitle;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.webview_prgressbar1));
        progressBar.setLayoutParams(new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.FILL_PARENT, 3, 0, 0));
        addView(progressBar);
        this.setWebChromeClient(new WebChromeClient());
        initWebView();
    }

    private void initWebView() {

        WebSettings wSet = getSettings();
        wSet.setJavaScriptEnabled(true);
        wSet.setJavaScriptCanOpenWindowsAutomatically(true);
        wSet.setBuiltInZoomControls(false); // 不显示放大缩小 controler
        wSet.setSupportZoom(false); // 不可以缩放

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams)progressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
    }

    public void setProgressbar(ProgressBar progressBar) {
        if (progressBar == null)
            return;
        progressBar.setVisibility(VISIBLE);
        this.progressBar = progressBar;
    }

    private class WebChromeClient extends android.webkit.WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (progressBar == null)
                return;
            if (newProgress == 100) {
                progressBar.setVisibility(GONE);
            } else {
                if (progressBar.getVisibility() == GONE)
                    progressBar.setVisibility(VISIBLE);
                progressBar.setProgress(newProgress);
            }
            LogUtils.d("progress = " + newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mWebTitle = title;
            LogUtils.d(TAG, "title:" + title);
        }
    }
}