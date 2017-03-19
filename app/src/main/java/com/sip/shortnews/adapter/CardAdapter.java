package com.sip.shortnews.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
    private List<SocialMediaItem> mSocialMediaArray;
    private Context mContext;
    private IFCardEvent ifCardEvent;
    public CardAdapter(Context context,List<SocialMediaItem> socialMediaItems,IFCardEvent event){
        mSocialMediaArray = socialMediaItems;
        mContext = context;
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
        Log.e("--card--","instantiateItem"+position);
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
        final ScrollView scrollView = (ScrollView)v.findViewById(R.id.scrollImage);
        final ImageView postImage = (ImageView)v.findViewById(R.id.post_image_content);
        final ImageView playButton = (ImageView)v.findViewById(R.id.play_button);
        final ImageView gifPlayButton = (ImageView)v.findViewById(R.id.gif_play_button);

        switch (socialMediaItem.getSocial_content_type_id()){

            case 0:
                postImage.setVisibility(View.VISIBLE);
                playButton.setVisibility(View.VISIBLE);
                gifPlayButton.setVisibility(View.INVISIBLE);
                llSliderParent.setVisibility(View.GONE);
                if(links.length>1)
                    ImageLoader.getInstance().displayImage(links[1],postImage);
                break;

            case 1:
                playButton.setVisibility(View.INVISIBLE);
                gifPlayButton.setVisibility(View.INVISIBLE);
                if(links.length>1){
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                    postImage.setVisibility(View.GONE);
                    llSliderParent.setVisibility(View.VISIBLE);
                    ViewPager mainSlider = (ViewPager)v.findViewById(R.id.main_slide);
                    MainSliderAdapter mainSliderAdapter = new MainSliderAdapter(links);
                    mainSlider.setAdapter(mainSliderAdapter);
                    IndicatorViewPager indicatorViewPager = (IndicatorViewPager)v.findViewById(R.id.indicator);
                    indicatorViewPager.setImageVisible(3).setMainSlide(mainSlider);
                }else {
                    postImage.setVisibility(View.VISIBLE);
                    ImageLoader.getInstance().displayImage(links[0],postImage);
                }
            break;

            case 2:
                playButton.setVisibility(View.INVISIBLE);
                gifPlayButton.setVisibility(View.VISIBLE);
                postImage.setVisibility(View.VISIBLE);
                llSliderParent.setVisibility(View.GONE);
                final Uri uri = Uri.parse(links[0]);



            break;

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

        cardView.setMaxCardElevation(8);
        int padding = UtiliFunction.dpTopxInt(mContext,2);
        cardView.setContentPadding(-padding,-padding,-padding,-padding);
        container.addView(cardView);
        //startUpdate(container);
        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.e("--remove--","destroyItem at "+position);
        View view = (View) object;
        container.removeView(view);
        view = null;
    }
    public interface IFCardEvent{
        void onClickVideo(SocialMediaItem socialMediaItem);
    }
}
