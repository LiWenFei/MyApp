package com.lwf.share;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author wqr
 * @Description: 第三方授权登录和分享
 * @date 2015-2-6 下午4:52:23
 */
public class ShareUtil {

    private static ShareUtil instance;

    public static final SHARE_MEDIA[] DefaultDisplaylist = new SHARE_MEDIA[] {SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA
        .SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
        // SHARE_MEDIA.SMS
    };

    private static final String wxappId = "wxappId";
    private static final String wxappSecret = "wxappSecret";

    //    APP ID1105617406
//    APP KEYqWb9I7MAUMm4Xc8b
    private static final String qqappId = "qqappId";
    private static final String qqappKey = "qqappKey";

    public static ShareUtil getInstance() {

        if (null == instance) {
            synchronized (ShareUtil.class) {
                if (null == instance) {
                    instance = new ShareUtil();
                    init();
                }
            }
        }

        return instance;
    }

    private static void init() {
        //微信 appid appsecret
        PlatformConfig.setWeixin(wxappId, wxappSecret);

        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone(qqappId, qqappKey);
    }

    public ManualShareEngine doShare(Activity mActivity, SHARE_MEDIA mShareMedia) {
        return new ManualShareEngine(mActivity, mShareMedia);
    }

    public AutoShareEngine openShare(Activity mActivity) {
        return new AutoShareEngine(mActivity).setDisplaylist(DefaultDisplaylist);
    }

    /**
     * 删除授权(不难看出与授权的参数是一样的)
     * <p>
     * 注意在onActivityResult需要添加 mShareAPI.onActivityResult(requestCode, resultCode, data);
     *
     * @param mActivity
     * @param shareMedia
     * @param authListener
     */
    public UMShareAPI deleteOauthVerify(Activity mActivity, SHARE_MEDIA shareMedia, UMAuthListener authListener) {

        UMShareAPI mShareAPI = UMShareAPI.get(mActivity);
        mShareAPI.deleteOauth(mActivity, shareMedia, authListener);

        return mShareAPI;
    }

    public boolean isPlatformInstall(Activity mActiviy, SHARE_MEDIA mShareMedia) {

        UMShareAPI mShareAPI = UMShareAPI.get(mActiviy);
        return mShareAPI.isInstall(mActiviy, mShareMedia);
    }

    public boolean isWxInstall(Activity mActivity) {
        return isPlatformInstall(mActivity, SHARE_MEDIA.WEIXIN);
    }

    public boolean isQQInstall(Activity mActivity) {
        return isPlatformInstall(mActivity, SHARE_MEDIA.QQ);
    }

    public String getPlatformName(Context mContext, SHARE_MEDIA mShareMedia) {

        if (null == mShareMedia) {
            return "";
        }

        if (mShareMedia == SHARE_MEDIA.SINA) {
            return mContext.getString(R.string.sina);
        } else if (mShareMedia == SHARE_MEDIA.SMS) {
            return mContext.getString(R.string.sms);
        } else if (mShareMedia == SHARE_MEDIA.WEIXIN) {
            return mContext.getString(R.string.weixin_friend);
        } else if (mShareMedia == SHARE_MEDIA.WEIXIN_CIRCLE) {
            return mContext.getString(R.string.weixin_friend_circle);
        } else if (mShareMedia == SHARE_MEDIA.QQ) {
            return mContext.getString(R.string.qq_friend);
        } else if (mShareMedia == SHARE_MEDIA.QZONE) {
            return mContext.getString(R.string.qzone);
        } else {
            throw new IllegalArgumentException("Unsupport share media...");
        }
    }
}