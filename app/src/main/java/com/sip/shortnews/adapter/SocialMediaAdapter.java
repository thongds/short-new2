package com.sip.shortnews.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.transcode.BitmapToGlideDrawableTranscoder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
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
    private DisplayImageOptions mDisplayOption;
    private Context mContext;
    public SocialMediaAdapter(Context context,List<SocialMediaItem> socialMediaItems, VH.DetailClickListener detailClickListener){
        socialMediaItemList = socialMediaItems;
        mDetailClickListener = detailClickListener;
        mContext = context;
    }
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.social_media_item,parent,false);
        Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.loading);
        mDisplayOption =  new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .showImageOnLoading(drawable).imageScaleType(ImageScaleType.EXACTLY).build();
        Log.d("--debug--","onCreateViewHolder");
        return new VH(v,mDetailClickListener);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Log.d("--debug--","onBindViewHolder position "+position);
        holder.pushData(mContext,socialMediaItemList.get(position),position,mDisplayOption);
    }

    @Override
    public int getItemCount() {
        Log.d("--debug--","getItemCount");
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
        public void pushData(final Context context, final SocialMediaItem socialMediaItem, int clickPosition, DisplayImageOptions displayImageOptions){
            GradientDrawable gd = (GradientDrawable) mTagPost.getBackground().getCurrent();
            gd.setColor(Color.parseColor(socialMediaItem.getColor_tag()));
            ImageLoader.getInstance().displayImage(socialMediaItem.getSocial_logo(),mSocialLogo);
            ImageLoader.getInstance().displayImage(socialMediaItem.getFanpage_logo(),mPageLogo);
            String[] post_image = socialMediaItem.getPost_image_url().split(socialMediaItem.getSeparate_image_tag());
            mPageName.setText(socialMediaItem.getFanpage_name());
            mClickPosition = clickPosition;
            switch (socialMediaItem.getSocial_content_type_id()){
                case 0://video
                    ImageLoader.getInstance().displayImage(socialMediaItem.getVideo_tag(),mVideoTag);
                    if(post_image!=null &&post_image.length>1)
                        //ImageLoader.getInstance().displayImage(post_image[1],mPostImage,displayImageOptions);
                        playImage(context,post_image[1],mPostImage);
                    mVideoTag.setVisibility(View.VISIBLE);
                    if(socialMediaItem.getSocial_name().equals("youtube")) {
                        mYoutubePlay.setVisibility(View.VISIBLE);
                        mPlayImage.setVisibility(View.INVISIBLE);
                    }
                    else {
                        mYoutubePlay.setVisibility(View.INVISIBLE);
                        mPlayImage.setVisibility(View.VISIBLE);
                    }
                    break;

                case 1://image
                    mYoutubePlay.setVisibility(View.INVISIBLE);
                    mPlayImage.setVisibility(View.INVISIBLE);
                    mVideoTag.setVisibility(View.INVISIBLE);
                    if(post_image!=null &&post_image.length>0)
                        //ImageLoader.getInstance().displayImage(post_image[0],mPostImage,displayImageOptions);
                        playImage(context,post_image[0],mPostImage);
                    break;
                case 2://gif
                    mYoutubePlay.setVisibility(View.INVISIBLE);
                    mPlayImage.setVisibility(View.INVISIBLE);
                    mVideoTag.setVisibility(View.INVISIBLE);
                    mPostImage.setVisibility(View.VISIBLE);
                    //Glide.with(context).load(post_image[0]).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mPostImage);

                    final Uri uri = Uri.parse(post_image[0]);
                    final BitmapRequestBuilder<Uri, GlideDrawable> thumbRequest = Glide
                            .with(context)
                            .load(uri)
                            .asBitmap() // force first frame for Gif
                            .transcode(new BitmapToGlideDrawableTranscoder(context), GlideDrawable.class)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.youtube_logo)
                            .dontAnimate().dontTransform()
                            .error(R.drawable.loading)
                            .centerCrop();
                    thumbRequest.into(mPostImage);
                    mPostImage.setOnClickListener(new View.OnClickListener() { // or any parent of imgFeed
                        @Override public void onClick(View v) {

                            Glide
                                    .with(context)
                                    .load(uri) // load as usual (Gif as animated, other formats as Bitmap)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .placeholder(R.drawable.youtube_logo)
                                    .error(R.drawable.loading).centerCrop()
                                    .thumbnail(thumbRequest)
                                    .dontAnimate()
                                    .into(mPostImage);
                        }
                    });
                    break;
            }
            mPostTitle.setText(socialMediaItem.getTitle());
        }

        @Override
        public void onClick(View v) {
            Log.e("--test--","click id");
            mDetailClickListener.showDetail(mClickPosition,socialMediaItemList);
        }
        public interface DetailClickListener{
             void showDetail(int position,List<SocialMediaItem> data);
        }
        void playImage(Context context,String url, ImageView postImage){
            final Uri uri = Uri.parse(url);

            final BitmapRequestBuilder<Uri, GlideDrawable> thumbRequest = Glide
                    .with(context)
                    .load(uri)
                    .asBitmap() // force first frame for Gif
                    .transcode(new BitmapToGlideDrawableTranscoder(context), GlideDrawable.class)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .centerCrop();
            thumbRequest.into(postImage);

            Glide
                    .with(context)
                    .load(uri) // load as usual (Gif as animated, other formats as Bitmap)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading).centerCrop()
                    .thumbnail(thumbRequest)
                    .into(postImage);
        }
    }
}
