package com.lwf.share;

import android.app.Activity;
import android.graphics.BitmapFactory;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by dreamtang860 on 1/4/16.
 */
public class AutoShareEngine extends ShareEngine {

    protected SHARE_MEDIA[] displaylist;
    protected UMShareListener[] onShareResultListenerArray;

    public AutoShareEngine(Activity mActivity) {
        super(mActivity);
    }

    /**
     * 根据SHARE_MEDIA[]指定的需要显示的分享平台, 根据SHARE_MEDIA[]的顺序传入一一对应的ShareConfig
     *
     * @param shareConfigList
     * @return
     */
    public AutoShareEngine setOnShareContentList(ShareConfig... shareConfigList) {
        shareConfigs = shareConfigList;
        return this;
    }

    public AutoShareEngine setOnShareResultListenerArray(UMShareListener... onShareResultListenerArray) {
        this.onShareResultListenerArray = onShareResultListenerArray;
        return this;
    }

    public AutoShareEngine setDisplaylist(SHARE_MEDIA[] displaylist) {
        this.displaylist = displaylist;
        return this;
    }

    @Override
    public void show() {

        if (null == shareConfig && (null == shareConfigs || shareConfigs.length <= 0)) {
            throw new IllegalArgumentException("Config should no be null...");
        }

        if (null != shareConfigs && shareConfigs.length > 0) {

            ShareContent[] mShareContents = new ShareContent[shareConfigs.length];

            for (int i = 0; i < shareConfigs.length; i++) {
                mShareContents[i] = ShareContentFactory.getInstance().getShareContent(mActivity, shareConfigs[i]);
//                if (displaylist[i] == SHARE_MEDIA.SINA) {
//                    mShareContents[i].mText = mShareContents[i].mText + mShareContents[i].mTargetUrl;
//                }
            }

            ShareAction mShareAction = new ShareAction(mActivity).setDisplayList(displaylist).setContentList(mShareContents);

            if (null != onShareResultListenerArray) {
                mShareAction.setListenerList(onShareResultListenerArray);
            } else if (null != onShareResultListener) {
                mShareAction.setCallback(onShareResultListener);
            }

        } else if (null != shareConfig) {

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

            ShareContent[] mShareContents = new ShareContent[displaylist.length];

            for (int i = 0; i < displaylist.length; i++) {
                mShareContents[i] = ShareContentFactory.getInstance().getShareContent(mActivity, shareConfig);
                if (displaylist[i] == SHARE_MEDIA.WEIXIN_CIRCLE) {
                    String title = mShareContents[i].mTitle;
                    mShareContents[i].mTitle = mShareContents[i].mText;
                    mShareContents[i].mText = title;
                }
            }

            ShareAction mShareAction = new ShareAction(mActivity).setDisplayList(displaylist).setContentList(mShareContents);
//                    .withText(null == shareConfig.getContent() ? "" : shareConfig.getContent())
//                    .withTitle(null == shareConfig.getTitle() ? "" : shareConfig.getTitle())
//                    .withTargetUrl(null == shareConfig.getTargetURL() ? "" : shareConfig.getTargetURL())
//                    .withFollow(null == shareConfig.getContent() ? "" : shareConfig.getContent())
//                    .withMedia(mImage);

            if (null != onShareBoardClickListener) {
                mShareAction.setShareboardclickCallback(onShareBoardClickListener);
            }

            if (null != onShareResultListener) {
                mShareAction.setCallback(onShareResultListener);
            }

            mShareAction.open();
        }
    }
}
