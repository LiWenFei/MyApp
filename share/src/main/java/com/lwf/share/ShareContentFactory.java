package com.lwf.share;

import android.app.Activity;
import android.graphics.BitmapFactory;

import com.umeng.socialize.ShareContent;
import com.umeng.socialize.media.UMImage;

/**
 * Created by dreamtang860 on 1/5/16.
 */
public class ShareContentFactory {

    private ShareContentFactory() {
    }

    public static ShareContentFactory getInstance() {
        return new ShareContentFactory();
    }

    public ShareContent getShareContent(Activity mActivity, ShareConfig shareConfig) {

        if (null == shareConfig) {
            return null;
        }

        UMImage mImage = null;

        if (!StringUtil.isEmpty(shareConfig.getImgURL())) {
            mImage = new UMImage(mActivity, shareConfig.getImgURL());
        } else if (null != shareConfig.getImgIcon()) {
            try {
                mImage = new UMImage(mActivity, BitmapFactory.decodeResource(mActivity.getResources(), shareConfig.getImgIcon()));
            } catch (Exception e) {
                mImage = new UMImage(mActivity, shareConfig.getImgIcon());
            }
        } else {
            mImage = new UMImage(mActivity, R.drawable.ic_launcher);
        }

        ShareContent mShareContent = new ShareContent();
        mShareContent.mMedia = mImage;
        mShareContent.mTargetUrl = shareConfig.getTargetURL();
        mShareContent.mText = shareConfig.getContent();
        mShareContent.mTitle = shareConfig.getTitle();

        return mShareContent;
    }

}
