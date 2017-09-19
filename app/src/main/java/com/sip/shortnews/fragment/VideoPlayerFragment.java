package com.sip.shortnews.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.sip.shortnews.MainActivity;
import com.sip.shortnews.R;


/**
 * Created by ssd on 8/28/16.
 */
public class VideoPlayerFragment extends  PFragment implements OnPreparedListener {

    static String mVideoUrl;
    MainActivity mainActivity;
    VideoView mPlayerView;
    ProgressBar progressBar;
    TextView tvError;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.player_video_layout,container,false);
            mPlayerView = (VideoView) v.findViewById(R.id.VideoView);
            progressBar = (ProgressBar)v.findViewById(R.id.progress);
            tvError = (TextView)v.findViewById(R.id.tv_error);
            mainActivity = (MainActivity)getActivity();
            ImageView closeImage = (ImageView)v.findViewById(R.id.btn_close);
            closeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.onBackPressed();
                }
            });
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(mPlayerView);
        Uri videoUri = Uri.parse(mVideoUrl);
        mPlayerView.setMediaController(mediaController);
        mPlayerView.setVideoURI(videoUri);
        mPlayerView.requestFocus();
        mPlayerView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mPlayerView.start();
                progressBar.setVisibility(View.GONE);
            }
        });
        mPlayerView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                tvError.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                return true;
            }
        });
//            mPlayerView.setOnPreparedListener(this);
//
//            //For now we just picked an arbitrary item to play.  More can be found at
//            //https://archive.org/details/more_animation
//            if(mVideoUrl!=null && !mVideoUrl.isEmpty()){
//                mPlayerView.setVideoURI(Uri.parse(mVideoUrl));
//            }else{
//                Toast.makeText(getContext(),"can not play video now",Toast.LENGTH_LONG).show();
//            }


        return v;
    }
    public void setVideoUlr(String url){
        mVideoUrl = url;
    }

    @Override
    public void onPrepared() {
        mPlayerView.start();
    }
}
