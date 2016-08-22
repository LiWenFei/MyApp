package com.lwf.share;

/**
 * Created by dreamtang860 on 1/5/16.
 */
public class ShareConfig {

    private String title;
    private String content;
    private String imgURL;
    private Integer imgIcon;
    private String targetURL;
    private String websiteURL;

    private ShareConfig() {

    }

    public static class Builder {

        private final ShareConfig mShareConfig;

        public Builder() {
            this.mShareConfig = new ShareConfig();
        }

        public Builder setTitle(String title) {
            mShareConfig.title = title;
            return this;
        }

        public Builder setContent(String content) {
            mShareConfig.content = content;
            return this;
        }

        public Builder setImgURL(String imgURL) {
            mShareConfig.imgURL = imgURL;
            return this;
        }

        public Builder setImgIcon(Integer imgIcon) {
            mShareConfig.imgIcon = imgIcon;
            return this;
        }

        public Builder setTargetURL(String targetURL) {
            mShareConfig.targetURL = targetURL;
            return this;
        }

        public Builder setWebsiteURL(String websiteURL) {
            mShareConfig.websiteURL = websiteURL;
            return this;
        }

        public ShareConfig build() {
            return mShareConfig;
        }

    }

    public String getTitle() {
        return title;
    }

    public ShareConfig setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ShareConfig setContent(String content) {
        this.content = content;
        return this;
    }

    public String getImgURL() {
        return imgURL;
    }

    public ShareConfig setImgURL(String imgURL) {
        this.imgURL = imgURL;
        return this;
    }

    public Integer getImgIcon() {
        return imgIcon;
    }

    public ShareConfig setImgIcon(Integer imgIcon) {
        this.imgIcon = imgIcon;
        return this;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public ShareConfig setTargetURL(String targetURL) {
        this.targetURL = targetURL;
        return this;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public ShareConfig setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
        return this;
    }

    @Override
    public String toString() {
        return "ShareConfig{" +
            "title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", imgURL='" + imgURL + '\'' +
            ", imgIcon=" + imgIcon +
            ", targetURL='" + targetURL + '\'' +
            ", websiteURL='" + websiteURL + '\'' +
            '}';
    }

}
