package com.lwf.share;

import android.app.Activity;
import android.graphics.BitmapFactory;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by dreamtang860 on 1/4/16.
 */
public class ManualShareEngine extends ShareEngine {

    private final SHARE_MEDIA mShareMedia;

    public ManualShareEngine(Activity mActivity, SHARE_MEDIA mShareMedia) {

        super(mActivity);

        this.mShareMedia = mShareMedia;

        if (null == mShareMedia) {
            throw new IllegalArgumentException("SHARE_MEDIA should not be null...");
        }
    }

    @Override
    public void show() {

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

        ShareAction mShareAction = new ShareAction(mActivity).setPlatform(mShareMedia);

        if (mShareMedia != SHARE_MEDIA.SMS) {
            mShareAction.withText(null == shareConfig.getContent() ? "" : shareConfig.getContent())
                .withTitle(null == shareConfig.getTitle() ? "" : shareConfig.getTitle())
                .withTargetUrl(null == shareConfig.getTargetURL() ? "" : shareConfig.getTargetURL()).withMedia(mImage);
        } else {
            mShareAction.withText(null == shareConfig.getContent() ? "" : shareConfig.getContent());
        }

        if (null != onShareBoardClickListener) {
            mShareAction.setShareboardclickCallback(onShareBoardClickListener);
        }

        if (null != onShareResultListener) {
            mShareAction.setCallback(onShareResultListener);
        }

        mShareAction.share();
    }
}
