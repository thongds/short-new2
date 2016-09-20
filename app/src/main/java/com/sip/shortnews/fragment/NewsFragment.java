package com.sip.shortnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.sip.shortnews.MainActivity;
import com.sip.shortnews.R;
import com.sip.shortnews.adapter.NewsMediaAdapter;
import com.sip.shortnews.model.NewsHomeItem;
import com.sip.shortnews.service.home_api.HomeMediaService;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_medial_holder_layout,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mainActivity = (MainActivity)getActivity();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRefresh = (SwipeRefreshLayout)view.findViewById(R.id.refresh);
        mRefresh.setEnabled(true);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mRefresh.isNestedScrollingEnabled()){
                    callService();
                }
            }
        });
        callService();

        return view;
    }

    private void callService() {
        HomeMediaService.Service service = HomeMediaService.service();
        service.getNews().enqueue(new Callback<List<NewsHomeItem>>() {
            @Override
            public void onResponse(Call<List<NewsHomeItem>> call, Response<List<NewsHomeItem>> response) {
                response.isSuccessful();
                List<NewsHomeItem> list = response.body();
                NewsMediaAdapter newsMediaAdapter = new NewsMediaAdapter(mainActivity,list,ifItemClick);
                mRecyclerView.setAdapter(newsMediaAdapter);
                mRecyclerView.addOnScrollListener(onScrollListener);
                mRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<NewsHomeItem>> call, Throwable t) {
                t.printStackTrace();
                mRefresh.setRefreshing(false);
                Toast.makeText(getContext(),"network error!",Toast.LENGTH_LONG).show();
            }

        });
    }

    OnScrollListener onScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                // Do something
            } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                // Do something
            } else {
                // Do something
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };
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
