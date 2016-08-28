package com.sip.shortnews.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sip.shortnews.MainActivity;
import com.sip.shortnews.R;
import com.sip.shortnews.adapter.SocialMediaAdapter;
import com.sip.shortnews.model.SocialMediaItem;
import com.sip.shortnews.service.home_api.HomeMediaService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ssd on 8/20/16.
 */
public class SocialFragment extends PFragment {
    private LinearLayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social_holder_layout,container,false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        HomeMediaService.service().getSocial().enqueue(new Callback<List<SocialMediaItem>>() {
            @Override
            public void onResponse(Call<List<SocialMediaItem>> call, Response<List<SocialMediaItem>> response) {
                if(response.isSuccessful()) {
                    List<SocialMediaItem> socialMediaItems = response.body();

                    SocialMediaAdapter socialMediaAdapter = new SocialMediaAdapter(socialMediaItems, detailClickListener);
                    recyclerView.setAdapter(socialMediaAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<SocialMediaItem>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return view;
    }
    SocialMediaAdapter.VH.DetailClickListener  detailClickListener = new SocialMediaAdapter.VH.DetailClickListener() {
        @Override
        public void showDetail(int position, List<SocialMediaItem> data) {
            MainActivity mainActivity = (MainActivity)getActivity();
            VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
            videoPlayerFragment.setVideoUlr(data.get(position).getVideo_link());
            DetailViewPageFragment detailViewPageFragment = new DetailViewPageFragment();
            detailViewPageFragment.setArg(data,position);
            mainActivity.replaceBackground(videoPlayerFragment);
        }
    };

}
