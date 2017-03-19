package com.sip.shortnews.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.sip.shortnews.R;
import com.sip.shortnews.model.HeaderModel;
import com.sip.shortnews.model.SocialMediaItem;

import java.util.List;

/**
 * Created by ssd on 8/20/16.
 */
public class SocialMediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static List<SocialMediaItem> socialMediaItemList;
    private VH.DetailClickListener mDetailClickListener;


    private HeaderModel mHeaderModel;
    private DisplayImageOptions mDisplayOption;
    private Context mContext;
    private int TYPE_HEADER = 0;
    private  int TYPE_ITEM = 1;

    public SocialMediaAdapter(Context context,List<SocialMediaItem> socialMediaItems, VH.DetailClickListener detailClickListener){
        socialMediaItemList = socialMediaItems;
        mDetailClickListener = detailClickListener;
        mContext = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_ITEM){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.social_media_item,parent,false);
            Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.loading);
            mDisplayOption =  new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                    .showImageOnLoading(drawable).imageScaleType(ImageScaleType.EXACTLY).build();
            return new VH(v,mDetailClickListener);
        }
        if(viewType == TYPE_HEADER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_media_view_header,parent,false);
            NewsMediaAdapter.MyViewHolderHeader ViewHolder = new NewsMediaAdapter.MyViewHolderHeader(v);
            Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.loading);
            mDisplayOption =  new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                    .showImageOnLoading(drawable).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
            return ViewHolder;
        }
        throw new RuntimeException("can't find Type");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsMediaAdapter.MyViewHolderHeader){
            NewsMediaAdapter.MyViewHolderHeader myViewHolderHeader = (NewsMediaAdapter.MyViewHolderHeader)holder;
            if (mHeaderModel != null)
                myViewHolderHeader.pushData(mContext, mHeaderModel);
        }
        if(holder instanceof VH){
            VH vh = (VH)holder;
            vh.pushData(mContext,socialMediaItemList.get(position-1),position-1,mDisplayOption);
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
        if(socialMediaItemList!=null)
            return socialMediaItemList.size() + 1;
        return 0;
    }
    public HeaderModel getmHeaderModel() {
        return mHeaderModel;
    }

    public void setmHeaderModel(HeaderModel mHeaderModel) {
        this.mHeaderModel = mHeaderModel;
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
        ImageView mGifPlay;
        SimpleDraweeView facebookLoadImage;
        int mClickPosition;

        DetailClickListener mDetailClickListener;
        public VH(View v ,DetailClickListener detailClickListener) {
            super(v);
            mDetailClickListener  = detailClickListener;
            mSocialLogo = (ImageView)v.findViewById(R.id.paper_logo);
            mPageLogo = (ImageView)v.findViewById(R.id.post_image);
            mPostImage = (ImageView)v.findViewById(R.id.post_content);
            facebookLoadImage = (SimpleDraweeView)v.findViewById(R.id.facebook_image_view);
            mTagPost = (RelativeLayout)v.findViewById(R.id.corner_left);
            mVideoTag = (ImageView)v.findViewById(R.id.video_tag);
            mPostTitle = (TextView)v.findViewById(R.id.post_title);
            mPageName = (TextView)v.findViewById(R.id.page_name);
            mPlayImage = (ImageView)v.findViewById(R.id.play_image_social);
            mYoutubePlay = (ImageView)v.findViewById(R.id.youtube_play_image_social);
            mGifPlay = (ImageView)v.findViewById(R.id.gif_play_image_social);
            RelativeLayout relativeLayout = (RelativeLayout)v.findViewById(R.id.rl_video);
            relativeLayout.setOnClickListener(this);
            mPlayImage.setOnClickListener(this);
            mYoutubePlay.setOnClickListener(this);
            mPostTitle.setOnClickListener(this);
        }
        public void pushData(final Context context, final SocialMediaItem socialMediaItem, int clickPosition, DisplayImageOptions displayImageOptions){
            GradientDrawable gd = (GradientDrawable) mTagPost.getBackground().getCurrent();
            gd.setColor(Color.parseColor(socialMediaItem.getColor_tag()));
            ImageLoader.getInstance().displayImage(socialMediaItem.getSocial_logo(),mSocialLogo);
            ImageLoader.getInstance().displayImage(socialMediaItem.getFanpage_logo(),mPageLogo);
            String[] post_image = socialMediaItem.getPost_image_url().split(socialMediaItem.getSeparate_image_tag());
            mPageName.setText(socialMediaItem.getFanpage_name());
            mClickPosition = clickPosition;
            mPostImage.setVisibility(View.VISIBLE);
            facebookLoadImage.setVisibility(View.INVISIBLE);

            switch (socialMediaItem.getSocial_content_type_id()){

                case 0://video
                    ImageLoader.getInstance().displayImage(socialMediaItem.getVideo_tag(),mVideoTag);
                    if(post_image!=null &&post_image.length>1)
                        //ImageLoader.getInstance().displayImage(post_image[1],mPostImage,displayImageOptions);
                        playImage(context,post_image[1],mPostImage);
                    mVideoTag.setVisibility(View.VISIBLE);
                    mGifPlay.setVisibility(View.INVISIBLE);
                    mYoutubePlay.setVisibility(View.INVISIBLE);
                    mPlayImage.setVisibility(View.VISIBLE);
                    break;
                case 1://image
                    mYoutubePlay.setVisibility(View.INVISIBLE);
                    mPlayImage.setVisibility(View.INVISIBLE);
                    mVideoTag.setVisibility(View.INVISIBLE);
                    mGifPlay.setVisibility(View.INVISIBLE);
                    mPostImage.setOnClickListener(this);
                    if(post_image!=null &&post_image.length>0)
                        //ImageLoader.getInstance().displayImage(post_image[0],mPostImage,displayImageOptions);
                        playImage(context,post_image[0],mPostImage);
                    break;
                case 2://gif
                    mYoutubePlay.setVisibility(View.INVISIBLE);
                    mPlayImage.setVisibility(View.INVISIBLE);
                    mVideoTag.setVisibility(View.INVISIBLE);
                    facebookLoadImage.setVisibility(View.VISIBLE);
                   mGifPlay.setVisibility(View.VISIBLE);
//                    Glide.with(context).load(post_image[0]).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mPostImage);

                    final Uri uri = Uri.parse(post_image[0]);

                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setUri(uri)
                            .setAutoPlayAnimations(true)
                            .build();
                    //Set the DraweeView controller, and you should be good to go.
                    facebookLoadImage.setController(controller);


                    break;
                case 3 :// youtube
                    mYoutubePlay.setVisibility(View.VISIBLE);
                    mPlayImage.setVisibility(View.INVISIBLE);
                    if(post_image!=null &&post_image.length>0)
                        playImage(context,post_image[1],mPostImage);
                    break;
            }
            mPostTitle.setText(socialMediaItem.getTitle());
        }

        @Override
        public void onClick(View v) {
            mDetailClickListener.showDetail(mClickPosition,socialMediaItemList);
        }
        public interface DetailClickListener{
             void showDetail(int position,List<SocialMediaItem> data);
        }
        void playImage(Context context,String url, ImageView postImage){
            ImageLoader.getInstance().displayImage(url,postImage);
//            ImageLoader.getInstance().displayImage(url,postImage);
//            final Uri uri = Uri.parse(url);
//            postImage.setImageURI(uri);

        }
    }
}
