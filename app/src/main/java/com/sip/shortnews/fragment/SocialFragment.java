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

import com.sip.shortnews.MainActivity;
import com.sip.shortnews.R;
import com.sip.shortnews.Utilies.SocialContentType;
import com.sip.shortnews.VideoYoutubePlayerActivity;
import com.sip.shortnews.adapter.SocialMediaAdapter;
import com.sip.shortnews.listener.EndlessRecyclerViewScrollListener;
import com.sip.shortnews.model.NewsHomeItem;
import com.sip.shortnews.model.SocialMediaItem;
import com.sip.shortnews.model.SocialMediaSection;
import com.sip.shortnews.service.home_api.HomeMediaService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.xip.errorview.ErrorView;

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
    private SocialFragment mFragment;
    private ErrorView mErrorView;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social_holder_layout,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mList = new ArrayList<>();
        mSocialMediaAdapter = new SocialMediaAdapter(getActivity(),mList, detailClickListener);
        mRecyclerView.setAdapter(mSocialMediaAdapter);
        mErrorView = (ErrorView)view.findViewById(R.id.error_view);
        avLoadingIndicatorView = (AVLoadingIndicatorView)view.findViewById(R.id.loading);
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
        mErrorView.setOnRetryListener(new ErrorView.RetryListener() {
            @Override
            public void onRetry() {
                mFragment.callService(0,false);
            }
        });
        return view;
    }

    private void callService(final int page, final boolean isRefreshing) {
        mErrorView.setVisibility(View.GONE);
        avLoadingIndicatorView.show();
        HomeMediaService.service().getSocial(page).enqueue(new Callback<SocialMediaSection>() {
            @Override
            public void onResponse(Call<SocialMediaSection> call, Response<SocialMediaSection> response) {
                if(response.isSuccessful()) {
                    SocialMediaSection socialMediaSection = response.body();
                    if(isRefreshing){
                        mRefresh.setRefreshing(false);
                        mScrollListener.reset();
                        mList.clear();
                    }
                    if(socialMediaSection.getData().size()>0){
                        mList.addAll(socialMediaSection.getData());
                        if(page == 0){
                            mSocialMediaAdapter.setmHeaderModel(socialMediaSection.getMessage());
                        }
                        mSocialMediaAdapter.notifyDataSetChanged();
                    }
                    avLoadingIndicatorView.hide();
                }
            }

            @Override
            public void onFailure(Call<SocialMediaSection> call, Throwable t) {
                //Toast.makeText(getContext(),"network error!",Toast.LENGTH_LONG).show();
                mRecyclerView.setVisibility(View.GONE);
                avLoadingIndicatorView.setVisibility(View.GONE);
                if(page == 0 && isRefreshing == false)
                    mErrorView.setVisibility(View.GONE);
                mRefresh.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }

    SocialMediaAdapter.VH.DetailClickListener  detailClickListener = new SocialMediaAdapter.VH.DetailClickListener() {
        @Override
        public void showDetail(int position, List<SocialMediaItem> data) {
            MainActivity mainActivity = (MainActivity)getActivity();
            if(data.get(position).getSocial_content_type_id() == SocialContentType.YOUTUBE.getValue()){
                String[] splitData = data.get(position).getPost_image_url().split(data.get(position).getSeparate_image_tag());
                if(data.get(position).getSocial_content_type_id() == SocialContentType.YOUTUBE.getValue()){
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

    @Override
    public void onResume() {
        super.onResume();
        mFragment = this;
    }
}
