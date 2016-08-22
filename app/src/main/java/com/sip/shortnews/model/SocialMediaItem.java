package com.sip.shortnews.model;

import android.graphics.Bitmap;

/**
 * Created by ssd on 8/20/16.
 */
public class SocialMediaItem {



    private boolean isYoutube;
    private boolean isVideo;
    private String mTitleColor;
    private String mPostContent;
    private Bitmap mImagePost;
    private Bitmap mPageLogo;
    private String mPageName;
    private Bitmap mSocialLogo;
    private String mTagColor;
    private Bitmap mVideoTagImage;
    public boolean isYoutube() {
        return isYoutube;
    }

    public void setYoutube(boolean youtube) {
        isYoutube = youtube;
    }
    public String getmPageName() {
        return mPageName;
    }

    public void setmPageName(String mPageName) {
        this.mPageName = mPageName;
    }
    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getmTitleColor() {
        return mTitleColor;
    }

    public void setmTitleColor(String mTitleColor) {
        this.mTitleColor = mTitleColor;
    }

    public String getmPostContent() {
        return mPostContent;
    }

    public void setmPostContent(String mPostContent) {
        this.mPostContent = mPostContent;
    }

    public Bitmap getmImagePost() {
        return mImagePost;
    }

    public void setmImagePost(Bitmap mImagePost) {
        this.mImagePost = mImagePost;
    }

    public Bitmap getmPageLogo() {
        return mPageLogo;
    }

    public void setmPageLogo(Bitmap mPageLogo) {
        this.mPageLogo = mPageLogo;
    }

    public Bitmap getmSocialLogo() {
        return mSocialLogo;
    }

    public void setmSocialLogo(Bitmap mSocialLogo) {
        this.mSocialLogo = mSocialLogo;
    }

    public String getmTagColor() {
        return mTagColor;
    }

    public void setmTagColor(String mTagColor) {
        this.mTagColor = mTagColor;
    }

    public Bitmap getmVideoTagImage() {
        return mVideoTagImage;
    }

    public void setmVideoTagImage(Bitmap mVideoTagImage) {
        this.mVideoTagImage = mVideoTagImage;
    }



}
