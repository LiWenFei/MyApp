package com.lwf.share;

import android.app.Activity;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.Serializable;

/**
 * Created by dreamtang860 on 1/4/16.
 */
public abstract class ShareEngine implements Serializable {

    protected final Activity mActivity;

    protected ShareConfig shareConfig;
    protected ShareConfig[] shareConfigs;

    protected ShareBoardlistener onShareBoardClickListener;
    protected UMShareListener onShareResultListener;

    public ShareEngine(Activity mActivity) {
        this.mActivity = mActivity;
        if (null == mActivity) {
            throw new IllegalArgumentException("Activity should not be null here...");
        }
    }

    public ShareEngine setShareConfig(ShareConfig shareConfig) {
        this.shareConfig = shareConfig;
        return this;
    }

    public ShareEngine setShareConfigs(ShareConfig... shareConfigs) {
        this.shareConfigs = shareConfigs;
        return this;
    }

    public ShareEngine setOnShareBoardClickListener(ShareBoardlistener onShareBoardClickListener) {
        this.onShareBoardClickListener = onShareBoardClickListener;
        return this;
    }

    public ShareEngine setOnShareResultListener(UMShareListener onShareResultListener) {
        this.onShareResultListener = onShareResultListener;
        return this;
    }

    public abstract void show();

}
