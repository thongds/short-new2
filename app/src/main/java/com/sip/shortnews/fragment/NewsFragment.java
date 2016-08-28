package com.sip.shortnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_medial_holder_layout,container,false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        HomeMediaService.Service service = HomeMediaService.service();
        service.getNews().enqueue(new Callback<List<NewsHomeItem>>() {
            @Override
            public void onResponse(Call<List<NewsHomeItem>> call, Response<List<NewsHomeItem>> response) {
                    response.isSuccessful();
                    List<NewsHomeItem> list = response.body();
                    Log.e("--response--", list.get(0).getFull_link());
                    NewsMediaAdapter newsMediaAdapter = new NewsMediaAdapter(list);
                    recyclerView.setAdapter(newsMediaAdapter);
                    recyclerView.addOnScrollListener(onScrollListener);
            }

            @Override
            public void onFailure(Call<List<NewsHomeItem>> call, Throwable t) {
                t.printStackTrace();
                Log.e("--response--",t.getMessage());
            }
        });

        return view;
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

}
