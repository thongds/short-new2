package com.sip.shortnews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sip.shortnews.MainActivity;
import com.sip.shortnews.R;
import com.sip.shortnews.VideoYoutubePlayerActivity;
import com.sip.shortnews.adapter.CardAdapter;
import com.sip.shortnews.adapter.NewsMediaAdapter;
import com.sip.shortnews.model.SocialMediaItem;

import java.util.List;

/**
 * Created by ssd on 8/27/16.
 */
public class DetailViewPageFragment extends PFragment implements CardAdapter.IFCardEvent {

    int mInitPosition;
    List<SocialMediaItem> mSocialMediaItemList;
    private MainActivity mainActivity;
    public void setArg(List<SocialMediaItem> socialMediaItemList,int position){
        mSocialMediaItemList = socialMediaItemList;
        mInitPosition = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_view_page_layout,container,false);
        ViewPager viewPager = (ViewPager)v.findViewById(R.id.viewPager);

        mainActivity = (MainActivity) getActivity();
        LinearLayout linearLayout1 = (LinearLayout)v.findViewById(R.id.ll_close1);
        LinearLayout linearLayout2 = (LinearLayout)v.findViewById(R.id.ll_close2);
        CardAdapter cardAdapter = new CardAdapter(getContext(),mSocialMediaItemList,this);
        viewPager.setAdapter(cardAdapter);
        viewPager.setCurrentItem(mInitPosition);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onBackPressed();
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onBackPressed();
            }
        });
        return v;
    }
    public void updateCardView(){

    }
    @Override
    public void onClickVideo(SocialMediaItem socialMediaItem) {
        if(socialMediaItem.getSocial_name().equals("youtube")){
            Intent intent = new Intent(mainActivity, VideoYoutubePlayerActivity.class);
            intent.putExtra("ytID",socialMediaItem.getVideo_link());
            startActivity(intent);
            mainActivity.overridePendingTransition(R.anim.from_main_slide_in, R.anim.from_main_silde_out);
        }else {
            VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
            videoPlayerFragment.setVideoUlr(socialMediaItem.getVideo_link());
            mainActivity.replaceBackground(videoPlayerFragment);
        }
    }
}
