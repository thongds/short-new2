package com.sip.shortnews.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sip.shortnews.R;
import com.sip.shortnews.model.CardViewItem;
import com.sip.shortnews.model.SocialMediaItem;

import java.util.List;

/**
 * Created by ssd on 8/20/16.
 */
public class SocialMediaAdapter extends RecyclerView.Adapter<SocialMediaAdapter.VH> {
    public static List<SocialMediaItem> socialMediaItemList;
    private VH.DetailClickListener mDetailClickListener;

    public SocialMediaAdapter(List<SocialMediaItem> socialMediaItems, VH.DetailClickListener detailClickListener){
        socialMediaItemList = socialMediaItems;
        mDetailClickListener = detailClickListener;
    }
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.social_media_item,parent,false);
        return new VH(v,mDetailClickListener);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.pushData(socialMediaItemList.get(position),position);
    }

    @Override
    public int getItemCount() {
        if(socialMediaItemList!=null)
            return socialMediaItemList.size();
        return 0;
    }

    public static class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mPageLogo;
        ImageView mSocialLogo;
        ImageView mPostImage;
        ImageView mVideoTag;
        RelativeLayout mTagPost;
        TextView mPostTitle;
        TextView mPageName;
        ImageView mPlayImage;
        ImageView mYoutubePlay;
        int mClickPosition;
        DetailClickListener mDetailClickListener;
        public VH(View v ,DetailClickListener detailClickListener) {
            super(v);
            mDetailClickListener  = detailClickListener;
            mSocialLogo = (ImageView)v.findViewById(R.id.paper_logo);
            mPageLogo = (ImageView)v.findViewById(R.id.post_image);
            mPostImage = (ImageView)v.findViewById(R.id.post_content);
            mTagPost = (RelativeLayout)v.findViewById(R.id.corner_left);
            mVideoTag = (ImageView)v.findViewById(R.id.video_tag);
            mPostTitle = (TextView)v.findViewById(R.id.post_title);
            mPageName = (TextView)v.findViewById(R.id.page_name);
            mPlayImage = (ImageView)v.findViewById(R.id.play_image_social);
            mYoutubePlay = (ImageView)v.findViewById(R.id.youtube_play_image_social);
            RelativeLayout relativeLayout = (RelativeLayout)v.findViewById(R.id.rl_video);
            relativeLayout.setOnClickListener(this);
            mPlayImage.setOnClickListener(this);
            mYoutubePlay.setOnClickListener(this);
            mPostTitle.setOnClickListener(this);
        }
        public void pushData(final SocialMediaItem socialMediaItem,int clickPosition){
            GradientDrawable gd = (GradientDrawable) mTagPost.getBackground().getCurrent();
            gd.setColor(Color.parseColor(socialMediaItem.getColor_tag()));
            ImageLoader.getInstance().displayImage(socialMediaItem.getSocial_logo(),mSocialLogo);
            ImageLoader.getInstance().displayImage(socialMediaItem.getFanpage_logo(),mPageLogo);
            ImageLoader.getInstance().displayImage(socialMediaItem.getPost_image_url(),mPostImage);
            mPageName.setText(socialMediaItem.getFanpage_name());
            mClickPosition = clickPosition;
            if(socialMediaItem.getIs_video() == 1){
                ImageLoader.getInstance().displayImage(socialMediaItem.getVideo_tag(),mVideoTag);
                mVideoTag.setVisibility(View.VISIBLE);
                if(socialMediaItem.getSocial_name().equals("youtube")) {
                    mYoutubePlay.setVisibility(View.VISIBLE);
                    mPlayImage.setVisibility(View.INVISIBLE);
                }
                else {
                    mYoutubePlay.setVisibility(View.INVISIBLE);
                    mPlayImage.setVisibility(View.VISIBLE);
                }
            }else{

            }
           // mPostTitle.setTextColor(Color.parseColor(socialMediaItem.getmTitleColor()));
            mPostTitle.setText(socialMediaItem.getTitle());
        }

        @Override
        public void onClick(View v) {
            Log.e("--test--","click id");
            mDetailClickListener.showDetail(mClickPosition,socialMediaItemList);
//            switch (v.getId()){
//                case R.id.play_image_social:
//                    if(mDetailClickListener!=null)
//                        mDetailClickListener.showDetail(mPostImage,mData);
//                    break;
//                case   R.id.youtube_play_image_social:
//                    if(mDetailClickListener!=null)
//                        mDetailClickListener.showDetail(mPostImage,mData);
//                    break;
//                case R.id.post_content:
//                    if(mDetailClickListener!=null)
//                        mDetailClickListener.showDetail(mPostImage,mData);
//                    break;
//            }
        }
        public interface DetailClickListener{
             void showDetail(int position,List<SocialMediaItem> data);
        }
    }
}
