package com.sip.shortnews.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.sip.shortnews.MainActivity;
import com.sip.shortnews.R;

/**
 * Created by ssd on 8/28/16.
 */
public class VideoPlayerFragment extends  PFragment implements OnPreparedListener {
    EMVideoView emVideoView;
    static String mVideoUrl;
    MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.player_video_layout,container,false);
            emVideoView = (EMVideoView)v.findViewById(R.id.video_view);

            mainActivity = (MainActivity)getActivity();
            ImageView closeImage = (ImageView)v.findViewById(R.id.btn_close);
            closeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.onBackPressed();
                }
            });
            emVideoView.setOnPreparedListener(this);

            //For now we just picked an arbitrary item to play.  More can be found at
            //https://archive.org/details/more_animation
            emVideoView.setVideoURI(Uri.parse(mVideoUrl));
            return v;
    }
    public void setVideoUlr(String url){
        mVideoUrl = url;
    }
    @Override
    public void onPrepared() {
        emVideoView.start();
    }
}
