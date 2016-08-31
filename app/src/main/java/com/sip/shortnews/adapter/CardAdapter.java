package com.sip.shortnews.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sip.shortnews.R;
import com.sip.shortnews.Utilies.UtiliFunction;
import com.sip.shortnews.model.SocialMediaItem;
import com.sip.shortnews.view_customize.IndicatorViewPager;
import com.sip.shortnews.view_customize.MainSliderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssd on 8/27/16.
 */
public class CardAdapter extends PagerAdapter {
    private List<CardView> mViews;
    private List<SocialMediaItem> mSocialMediaArray;
    private Context mContext;
    private IFCardEvent ifCardEvent;
    public CardAdapter(Context context,List<SocialMediaItem> socialMediaItems,IFCardEvent event){
        mSocialMediaArray = socialMediaItems;
        mContext = context;
        mViews = new ArrayList<>();
        ifCardEvent = event;
    }
    @Override
    public int getCount() {
        return  mSocialMediaArray.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.social_detail_layout,container,false);
        final SocialMediaItem socialMediaItem = mSocialMediaArray.get(position);
        LinearLayout llSliderParent  = (LinearLayout)v.findViewById(R.id.slider_ll);
        String[] links = socialMediaItem.getPost_image_url().split(socialMediaItem.getSeparate_image_tag());
        CardView cardView = (CardView)v.findViewById(R.id.cardView);
        LinearLayout detailHeader = (LinearLayout)v.findViewById(R.id.detail_header);
        TextView socialName = (TextView)v.findViewById(R.id.detail_header_title);
        TextView fanpageName = (TextView)v.findViewById(R.id.fanpage_name);
        CircularImageView fanpageAvatar =  (CircularImageView)v.findViewById(R.id.fanpage_avatar);
        TextView statusTitle = (TextView)v.findViewById(R.id.status_title);
        ImageView postImage = (ImageView)v.findViewById(R.id.post_image_content);
        ImageView playButton = (ImageView)v.findViewById(R.id.play_button);
        if(links.length>1){
            postImage.setVisibility(View.GONE);
            llSliderParent.setVisibility(View.VISIBLE);
            ViewPager mainSlider = (ViewPager)v.findViewById(R.id.main_slide);
            MainSliderAdapter mainSliderAdapter = new MainSliderAdapter(links);
            mainSlider.setAdapter(mainSliderAdapter);
            IndicatorViewPager indicatorViewPager = (IndicatorViewPager)v.findViewById(R.id.indicator);
            indicatorViewPager.setImageVisible(4).setMainSlide(mainSlider);
        }else{
            postImage.setVisibility(View.VISIBLE);
            llSliderParent.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(socialMediaItem.getPost_image_url(),postImage);
        }
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifCardEvent.onClickVideo(socialMediaItem);
            }
        });
        ImageLoader.getInstance().displayImage(socialMediaItem.getFanpage_logo(),fanpageAvatar);
        socialName.setText(socialMediaItem.getSocial_name());
        detailHeader.setBackgroundColor(Color.parseColor(socialMediaItem.getColor_tag()));
        fanpageName.setText(socialMediaItem.getFanpage_name());
        statusTitle.setText(socialMediaItem.getTitle());

        if(socialMediaItem.getIs_video() == 1){
            playButton.setVisibility(View.VISIBLE);
        }else {
            playButton.setVisibility(View.INVISIBLE);
        }

        container.addView(cardView);
        cardView.setMaxCardElevation(18);
        int padding = UtiliFunction.dpTopxInt(mContext,2);
        cardView.setContentPadding(-padding,-padding,-padding,-padding);
        mViews.add(cardView);
        startUpdate(container);
        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }
    public interface IFCardEvent{
        void onClickVideo(SocialMediaItem socialMediaItem);
    }
}
