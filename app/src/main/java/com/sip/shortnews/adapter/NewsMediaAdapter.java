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

import com.sip.shortnews.R;
import com.sip.shortnews.model.CardViewItem;

import java.util.List;

/**
 * Created by ssd on 8/19/16.
 */
public class NewsMediaAdapter<V> extends RecyclerView.Adapter<NewsMediaAdapter.MyViewHolder>{

    private List<CardViewItem> mCardViewItem;

    public NewsMediaAdapter(List<CardViewItem> cardViewItems){
        mCardViewItem = cardViewItems;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_media_view_item,parent,false);
        MyViewHolder ViewHolder = new MyViewHolder(v);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CardViewItem cardViewItem = mCardViewItem.get(position);
        Log.e("--test--","onBind "+position);
        holder.pushData(cardViewItem);
    }

    @Override
    public int getItemCount() {
        if(mCardViewItem!=null)
            return mCardViewItem.size();
        return 0;
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mPageLogo;
        ImageView mPostImage;
        ImageView mVideoTag;
        RelativeLayout mTagPost;
        TextView mPostTitle;
        TextView mPostContent;
        ImageView mPlayImage;
        ImageView mSocialLogo;
        TextView mPageName;
        public MyViewHolder(View v){
            super(v);
            mPageLogo = (ImageView)v.findViewById(R.id.paper_logo);
            mPostImage = (ImageView)v.findViewById(R.id.post_image);
            mTagPost = (RelativeLayout)v.findViewById(R.id.corner_left);
            mVideoTag = (ImageView)v.findViewById(R.id.video_tag);
            mPostTitle = (TextView)v.findViewById(R.id.post_title);
            mPostContent = (TextView)v.findViewById(R.id.post_content);
            mPlayImage = (ImageView)v.findViewById(R.id.play_image);
        }
        public void pushData(CardViewItem cardViewItem){
            GradientDrawable gd = (GradientDrawable) mTagPost.getBackground().getCurrent();
            gd.setColor(Color.parseColor(cardViewItem.getmTagColor()));
            mPageLogo.setImageBitmap(cardViewItem.getmPageLogo());
            mPostImage.setImageBitmap(cardViewItem.getmImagePost());
            if(cardViewItem.isVideo()){
                mVideoTag.setImageBitmap(cardViewItem.getmVideoTagImage());
                mVideoTag.setVisibility(View.VISIBLE);
                mPlayImage.setVisibility(View.VISIBLE);
            }
            mPostTitle.setTextColor(Color.parseColor(cardViewItem.getmTitleColor()));
            mPostTitle.setText(cardViewItem.getmPostTitle());
            mPostContent.setText(cardViewItem.getmPostContent());
        }
    }

}
