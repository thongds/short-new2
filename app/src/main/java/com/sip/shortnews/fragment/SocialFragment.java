package com.sip.shortnews.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.transcode.BitmapToGlideDrawableTranscoder;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sip.shortnews.MainActivity;
import com.sip.shortnews.R;
import com.sip.shortnews.VideoYoutubePlayerActivity;
import com.sip.shortnews.adapter.SocialMediaAdapter;
import com.sip.shortnews.listener.EndlessRecyclerViewScrollListener;
import com.sip.shortnews.model.NewsHomeItem;
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
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecyclerView;
    private EndlessRecyclerViewScrollListener mScrollListener ;
    private  List<SocialMediaItem> mList;
    private SocialMediaAdapter mSocialMediaAdapter;
    private int mPage = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social_holder_layout,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mList = new ArrayList<>();
        mSocialMediaAdapter = new SocialMediaAdapter(getActivity(),mList, detailClickListener);
        mRecyclerView.setAdapter(mSocialMediaAdapter);
        mScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mPage = page;
                callService(mPage,false);
            }
        };
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(mScrollListener);
        mRefresh = (SwipeRefreshLayout)view.findViewById(R.id.refresh);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mRefresh.isNestedScrollingEnabled()){
                    mList.clear();
                    callService(0,true);
                }
            }
        });
        callService(mPage,false);
        return view;
    }

    private void callService(int page,final boolean isRefreshing) {
        HomeMediaService.service().getSocial(page).enqueue(new Callback<List<SocialMediaItem>>() {
            @Override
            public void onResponse(Call<List<SocialMediaItem>> call, Response<List<SocialMediaItem>> response) {
                if(response.isSuccessful()) {
                    if(isRefreshing){
                        mRefresh.setRefreshing(false);
                        mScrollListener.reset();
                        mList.clear();
                    }
                    if(response.body().size()>0){
                        mList.addAll(response.body());
                        mSocialMediaAdapter.notifyDataSetChanged();

                    }


                }
            }

            @Override
            public void onFailure(Call<List<SocialMediaItem>> call, Throwable t) {
                Toast.makeText(getContext(),"network error!",Toast.LENGTH_LONG).show();
                mRefresh.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }

    SocialMediaAdapter.VH.DetailClickListener  detailClickListener = new SocialMediaAdapter.VH.DetailClickListener() {
        @Override
        public void showDetail(int position, List<SocialMediaItem> data) {
            MainActivity mainActivity = (MainActivity)getActivity();
            if(data.get(position).getSocial_content_type_id() == 0){
                String[] splitData = data.get(position).getPost_image_url().split(data.get(position).getSeparate_image_tag());
                if(data.get(position).getSocial_name().equals("youtube")){
                    Intent intent = new Intent(mainActivity, VideoYoutubePlayerActivity.class);
                    intent.putExtra("ytID",splitData[0]);
                    startActivity(intent);
                    mainActivity.overridePendingTransition(R.anim.from_main_slide_in, R.anim.from_main_silde_out);
                }else {
                    if(data.get(position).getPost_image_url()!=null && !data.get(position).getPost_image_url().isEmpty()) {
                        VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
                        videoPlayerFragment.setVideoUlr(splitData[0]);
                        mainActivity.replaceBackground(videoPlayerFragment);
                    }
                    else{
                        Toast.makeText(getContext(),"can not play video now",Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                DetailViewPageFragment detailViewPageFragment = new DetailViewPageFragment();
                detailViewPageFragment.setArg(data,position);
                mainActivity.replaceBackground(detailViewPageFragment);
            }
        }
    };


}
