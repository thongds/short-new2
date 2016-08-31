package com.sip.shortnews.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
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
import com.sip.shortnews.model.NewsHomeItem;

import java.util.List;

/**
 * Created by ssd on 8/19/16.
 */
public class NewsMediaAdapter<V> extends RecyclerView.Adapter<NewsMediaAdapter.MyViewHolder>{

    private List<NewsHomeItem> mCardViewItem;
    private  IFItemClick mListener;
    public NewsMediaAdapter(List<NewsHomeItem> cardViewItems,IFItemClick listener){
        mCardViewItem = cardViewItems;
        mListener = listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("--test--","onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_media_view_item,parent,false);
        MyViewHolder ViewHolder = new MyViewHolder(v);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewsHomeItem cardViewItem = mCardViewItem.get(position);
        Log.e("--test--","onBind "+position);
        holder.pushData(cardViewItem,mListener);
    }

    @Override
    public int getItemCount() {
        if(mCardViewItem!=null)
            return mCardViewItem.size();
        return 0;
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mPageLogo;
        ImageView mPostImage;
        ImageView mVideoTag;
        RelativeLayout mTagPost;
        TextView mPostTitle;
        TextView mPostContent;
        ImageView mPlayImage;
        ImageView mSocialLogo;
        TextView mPageName;
        IFItemClick itemClick;
        NewsHomeItem mCardViewItem;
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
        public void pushData(NewsHomeItem cardViewItem,IFItemClick ifItemClick){
            itemClick = ifItemClick;
            mCardViewItem = cardViewItem;
            GradientDrawable gd = (GradientDrawable) mTagPost.getBackground().getCurrent();
            gd.setColor(Color.parseColor(cardViewItem.getPaper_tag_color()));
            ImageLoader.getInstance().displayImage(cardViewItem.getPaper_logo(),mPageLogo);
            ImageLoader.getInstance().displayImage(cardViewItem.getPost_image(),mPostImage);
             if(cardViewItem.getIs_video() == 1){
                ImageLoader.getInstance().displayImage(cardViewItem.getVideo_tag_image(),mVideoTag);
                mVideoTag.setVisibility(View.VISIBLE);
                mPlayImage.setVisibility(View.VISIBLE);
                 mPlayImage.setOnClickListener(this);
            }
            mPostTitle.setTextColor(Color.parseColor(cardViewItem.getTitle_color()));
            mPostTitle.setText(cardViewItem.getPost_title());
            mPostContent.setText(cardViewItem.getPost_content());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.play_image:
                    itemClick.clickVideo(mCardViewItem);
                    break;
            }
        }
    }
    public interface IFItemClick{
        void clickVideo(NewsHomeItem data);
        void clickItem(NewsHomeItem data);
    }

}
