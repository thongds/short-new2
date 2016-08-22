package com.sip.shortnews.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sip.shortnews.R;
import com.sip.shortnews.model.CardViewItem;
import com.sip.shortnews.model.SocialMediaItem;

import java.util.List;

/**
 * Created by ssd on 8/20/16.
 */
public class SocialMediaAdapter extends RecyclerView.Adapter<SocialMediaAdapter.VH> {

    private List<SocialMediaItem> socialMediaItemList;


    public SocialMediaAdapter(List<SocialMediaItem> socialMediaItems){
        socialMediaItemList = socialMediaItems;
    }
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.social_media_item,parent,false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.pushData(socialMediaItemList.get(position));
    }

    @Override
    public int getItemCount() {
        if(socialMediaItemList!=null)
            return socialMediaItemList.size();
        return 0;
    }

    public static class VH extends RecyclerView.ViewHolder{
        ImageView mPageLogo;
        ImageView mSocialLogo;
        ImageView mPostImage;
        ImageView mVideoTag;
        RelativeLayout mTagPost;
        TextView mPostTitle;
        TextView mPageName;
        ImageView mPlayImage;
        ImageView mYoutubePlay;
        public VH(View v) {
            super(v);
            mSocialLogo = (ImageView)v.findViewById(R.id.paper_logo);
            mPageLogo = (ImageView)v.findViewById(R.id.post_image);
            mPostImage = (ImageView)v.findViewById(R.id.post_content);
            mTagPost = (RelativeLayout)v.findViewById(R.id.corner_left);
            mVideoTag = (ImageView)v.findViewById(R.id.video_tag);
            mPostTitle = (TextView)v.findViewById(R.id.post_title);
            mPageName = (TextView)v.findViewById(R.id.page_name);
            mPlayImage = (ImageView)v.findViewById(R.id.play_image_social);
            mYoutubePlay = (ImageView)v.findViewById(R.id.youtube_play_image_social);
        }
        public void pushData(SocialMediaItem socialMediaItem){
            GradientDrawable gd = (GradientDrawable) mTagPost.getBackground().getCurrent();
            gd.setColor(Color.parseColor(socialMediaItem.getmTagColor()));
            mSocialLogo.setImageBitmap(socialMediaItem.getmSocialLogo());
            mPageLogo.setImageBitmap(socialMediaItem.getmPageLogo());
            mPostImage.setImageBitmap(socialMediaItem.getmImagePost());
            mPageName.setText(socialMediaItem.getmPageName());
            if(socialMediaItem.isVideo()){
                mVideoTag.setImageBitmap(socialMediaItem.getmVideoTagImage());
                mVideoTag.setVisibility(View.VISIBLE);
                if(socialMediaItem.isYoutube()) {
                    mYoutubePlay.setVisibility(View.VISIBLE);
                    mPlayImage.setVisibility(View.INVISIBLE);
                }
                else {
                    mYoutubePlay.setVisibility(View.INVISIBLE);
                    mPlayImage.setVisibility(View.VISIBLE);
                }
            }
            mPostTitle.setTextColor(Color.parseColor(socialMediaItem.getmTitleColor()));
            mPostTitle.setText(socialMediaItem.getmPostContent());
        }
    }
}
