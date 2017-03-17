package com.sip.shortnews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.transcode.BitmapToGlideDrawableTranscoder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.sip.shortnews.R;
import com.sip.shortnews.model.CardViewItem;
import com.sip.shortnews.model.NewsHomeHeader;
import com.sip.shortnews.model.NewsHomeItem;
import com.sip.shortnews.model.NewsHomeSection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssd on 8/19/16.
 */
public class NewsMediaAdapter<V> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<NewsHomeItem> mCardViewItem ;

    private NewsHomeHeader mNewsHomeHeader;
    private  IFItemClick mListener;
    private DisplayImageOptions mDisplayOption;
    private Context mContext;
    private int TYPE_HEADER = 0;
    private  int TYPE_ITEM = 1;
    public NewsMediaAdapter(Context context, NewsHomeHeader newsHomeHeader,List<NewsHomeItem> newHomeList, IFItemClick listener){
        this.mNewsHomeHeader = newsHomeHeader;
        mCardViewItem = newHomeList;
        mListener = listener;
        mContext = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if(viewType == TYPE_ITEM){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_media_view_item,parent,false);
            MyViewHolderItem ViewHolder = new MyViewHolderItem(v);
            Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.loading);
            mDisplayOption =  new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                    .showImageOnLoading(drawable).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
            return ViewHolder;
        }
        if(viewType == TYPE_HEADER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_media_view_header,parent,false);
            MyViewHolderHeader ViewHolder = new MyViewHolderHeader(v);
            Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.loading);
            mDisplayOption =  new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                    .showImageOnLoading(drawable).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
            return ViewHolder;
        }
        throw  new RuntimeException("there are no ViewType = "+viewType +" match!");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolderHeader){
            MyViewHolderHeader myViewHolderHeader = (MyViewHolderHeader)holder;
            if (mNewsHomeHeader != null)
                myViewHolderHeader.pushData(mContext,mNewsHomeHeader);
        }
        if (holder instanceof MyViewHolderItem){
            NewsHomeItem cardViewItem = mCardViewItem.get(position - 1);
            MyViewHolderItem myViewHolder = (MyViewHolderItem)holder;
            myViewHolder.pushData(mContext,cardViewItem,mListener,mDisplayOption);
        }


    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return TYPE_HEADER;
        return  TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if(mCardViewItem!=null)
            return mCardViewItem.size()+1;
        return 0;
    }
    public NewsHomeHeader getmNewsHomeHeader() {
        return mNewsHomeHeader;
    }

    public void setmNewsHomeHeader(NewsHomeHeader mNewsHomeHeader) {
        this.mNewsHomeHeader = mNewsHomeHeader;
    }
    public static class MyViewHolderHeader extends RecyclerView.ViewHolder {

        private ImageView mSessionLogo;
        private TextView mSessionLabel;
        private TextView mSessionEventLabel;

        public MyViewHolderHeader(View itemView) {
            super(itemView);
            mSessionLabel = (TextView)itemView.findViewById(R.id.session_label);
            mSessionLogo = (ImageView)itemView.findViewById(R.id.session_logo);
            mSessionEventLabel = (TextView)itemView.findViewById(R.id.session_event);
        }
        public void pushData(Context context , NewsHomeHeader newsHomeSection){
            mSessionLabel.setText(newsHomeSection.getWelcome_message());
            mSessionEventLabel.setText(newsHomeSection.getEvent_message());
            MyViewHolderItem.playImage(newsHomeSection.getAvatar(),mSessionLogo);

        }
    }

    public static class  MyViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener{
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
        RelativeLayout mRlContent;
        ImageView mImageAds;
        public MyViewHolderItem(View v){
            super(v);
            mPageLogo = (ImageView)v.findViewById(R.id.paper_logo);
            mPostImage = (ImageView)v.findViewById(R.id.post_image);
            mTagPost = (RelativeLayout)v.findViewById(R.id.corner_left);
            mVideoTag = (ImageView)v.findViewById(R.id.video_tag);
            mPostTitle = (TextView)v.findViewById(R.id.post_title);
            mPostContent = (TextView)v.findViewById(R.id.post_content);
            mPlayImage = (ImageView)v.findViewById(R.id.play_image);
            mImageAds = (ImageView)v.findViewById(R.id.ads);
            mRlContent =  (RelativeLayout)v.findViewById(R.id.content);
        }
        public void pushData(Context context,NewsHomeItem cardViewItem,IFItemClick ifItemClick,DisplayImageOptions displayImageOptions){
            itemClick = ifItemClick;
            mCardViewItem = cardViewItem;
            if(mCardViewItem.getIs_ads() == 1){
                mRlContent.setVisibility(View.GONE);
                mImageAds.setVisibility(View.VISIBLE);
                playImage(mCardViewItem.getPost_image(),mImageAds);
            }else{
                mRlContent.setVisibility(View.VISIBLE);
                mImageAds.setVisibility(View.GONE);
                GradientDrawable gd = (GradientDrawable) mTagPost.getBackground().getCurrent();
                gd.setColor(Color.parseColor(cardViewItem.getPaper_tag_color().trim()));
                playImage(cardViewItem.getPaper_logo(),mPageLogo);
                playImage(cardViewItem.getPost_image(),mPostImage);
                if(cardViewItem.getIs_video() == 1){
                    ImageLoader.getInstance().displayImage(cardViewItem.getVideo_tag_image(),mVideoTag);
                    mVideoTag.setVisibility(View.VISIBLE);
                    mPlayImage.setVisibility(View.VISIBLE);
                    mPlayImage.setOnClickListener(this);
                }
                mPostTitle.setTextColor(Color.parseColor(cardViewItem.getTitle_color().trim()));
                mPostTitle.setText(cardViewItem.getPost_title());
                mPostContent.setText(cardViewItem.getPost_content());
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.play_image:
                    itemClick.clickVideo(mCardViewItem);
                    break;
            }
        }
        public static void playImage( String url, ImageView postImage){
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.loading)
                    .showImageForEmptyUri(R.drawable.loading)
                    .showImageOnFail(R.drawable.loading)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

            ImageLoader.getInstance().displayImage(url,postImage,options);
            //final Uri uri = Uri.parse(url);
//            Glide
//                    .with(context)
//                    .load(uri) // load as usual (Gif as animated, other formats as Bitmap)
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .placeholder(R.drawable.loading)
//                    .error(R.drawable.loading)
//                    .into(postImage);
        }
    }
    public interface IFItemClick{
        void clickVideo(NewsHomeItem data);
        void clickItem(NewsHomeItem data);
    }


}
