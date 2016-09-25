package com.sip.shortnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sip.shortnews.MainActivity;
import com.sip.shortnews.R;
import com.sip.shortnews.adapter.NewsMediaAdapter;
import com.sip.shortnews.listener.EndlessRecyclerViewScrollListener;
import com.sip.shortnews.model.NewsHomeItem;
import com.sip.shortnews.service.home_api.HomeMediaService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ssd on 8/14/16.
 */
public class NewsFragment extends PFragment {
    private LinearLayoutManager mLayoutManager;
    private MainActivity mainActivity;
    private SwipeRefreshLayout mRefresh;
    private  RecyclerView mRecyclerView;
    private  EndlessRecyclerViewScrollListener mScrollListener ;
    private  List<NewsHomeItem> mList;
    private NewsMediaAdapter mNewsMediaAdapter;
    private int mPage = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_medial_holder_layout,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mainActivity = (MainActivity)getActivity();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mList = new ArrayList<>();
        mNewsMediaAdapter = new NewsMediaAdapter(mainActivity,mList,ifItemClick);
        mRecyclerView.setAdapter(mNewsMediaAdapter);

        mScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mPage = page;
                callService(mPage,false);
            }
        };
        mRecyclerView.addOnScrollListener(mScrollListener);
        mRefresh = (SwipeRefreshLayout)view.findViewById(R.id.refresh);
        mRefresh.setEnabled(true);
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
        HomeMediaService.Service service = HomeMediaService.service();
        service.getNews(page).enqueue(new Callback<List<NewsHomeItem>>() {
            @Override
            public void onResponse(Call<List<NewsHomeItem>> call, Response<List<NewsHomeItem>> response) {
                if(response.isSuccessful()) {
                    if(response.body().size() >0){
                        mList.addAll(response.body());
                        mNewsMediaAdapter.notifyDataSetChanged();
                    }
                    if(isRefreshing){
                        mRefresh.setRefreshing(false);
                        mScrollListener.reset();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NewsHomeItem>> call, Throwable t) {
                t.printStackTrace();
                mRefresh.setRefreshing(false);
                Toast.makeText(getContext(),"network error!",Toast.LENGTH_LONG).show();
            }

        });
    }


    NewsMediaAdapter.IFItemClick ifItemClick = new NewsMediaAdapter.IFItemClick() {
        @Override
        public void clickVideo(NewsHomeItem data) {
            VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();

            if(data.getVideo_link()!=null && !data.getVideo_link().equals("")){
                videoPlayerFragment.setVideoUlr(data.getVideo_link());
                mainActivity.replaceBackground(videoPlayerFragment);
            }else{
                Toast.makeText(mainActivity,"can not play video now",Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void clickItem(NewsHomeItem data) {

        }
    };

}
