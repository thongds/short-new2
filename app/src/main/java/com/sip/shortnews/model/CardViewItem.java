package com.sip.shortnews.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by ssd on 8/19/16.
 */
public class CardViewItem  {
    private boolean isVideo;
    private String mPostTitle;
    private String mTitleColor;
    private String mPostContent;
    private Bitmap mImagePost;
    private Bitmap mPageLogo;
    private String mTagColor;
    private Bitmap mVideoTagImage;

    public String getmTitleColor() {
        return mTitleColor;
    }

    public void setmTitleColor(String mTitleColor) {
        this.mTitleColor = mTitleColor;
    }
    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getmPostTitle() {
        return mPostTitle;
    }

    public void setmPostTitle(String mPostTitle) {
        this.mPostTitle = mPostTitle;
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
