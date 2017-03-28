package com.sip.shortnews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.sip.shortnews.AppApplication;
import com.sip.shortnews.MainActivity;
import com.sip.shortnews.R;
import com.sip.shortnews.Utilies.AnalaticEnum;
import com.sip.shortnews.adapter.NewsMediaAdapter;
import com.sip.shortnews.listener.EndlessRecyclerViewScrollListener;
import com.sip.shortnews.model.HeaderModel;
import com.sip.shortnews.model.NewsHomeItem;
import com.sip.shortnews.model.NewsHomeSection;
import com.sip.shortnews.service.home_api.HomeMediaService;
import com.wang.avi.AVLoadingIndicatorView;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.xip.errorview.ErrorView;

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
    private HeaderModel mHeaderModel;
    private NewsMediaAdapter mNewsMediaAdapter;
    private ErrorView mErrorView;
    private int mPage = 0;
    private NewsFragment mFragment;
    private Context mContext;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private MainActivity mMainActive;
    private boolean mIsRefresh;
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_medial_holder_layout,container,false);
        avLoadingIndicatorView = (AVLoadingIndicatorView)view.findViewById(R.id.loading);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mErrorView = (ErrorView)view.findViewById(R.id.error_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mainActivity = (MainActivity)getActivity();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mList = new ArrayList<>();
        mContext = getContext();
        mNewsMediaAdapter = new NewsMediaAdapter(mainActivity, mHeaderModel,mList,ifItemClick);
        mRecyclerView.setAdapter(mNewsMediaAdapter);
        mFragment = this;
        mDilatingDotsProgressBar = (DilatingDotsProgressBar)view.findViewById(R.id.load_more_progress);
        mDilatingDotsProgressBar.hide();

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
        mErrorView.setOnRetryListener(new ErrorView.RetryListener() {
            @Override
            public void onRetry() {
                //Toast.makeText(mContext,"click retry",Toast.LENGTH_LONG).show();
                mFragment.callService(0,false);
            }
        });
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return  mIsRefresh;
            }
        });
        callService(mPage,false);

        return view;
    }

    private void callService(final int page, final boolean isRefreshing) {
        sentEvent(page);
        mIsRefresh = isRefreshing;
        mErrorView.setVisibility(View.INVISIBLE);
        if(page == 0 && isRefreshing == false)
            avLoadingIndicatorView.show();
        if(page!=0)
            mDilatingDotsProgressBar.show();
        HomeMediaService.Service service = HomeMediaService.service();
        service.getNews(page).enqueue(new Callback<NewsHomeSection>() {
            @Override
            public void onResponse(Call<NewsHomeSection> call, Response<NewsHomeSection> response) {
                if(response.isSuccessful()) {
                mFragment.updateData(page,response,isRefreshing);
//                    mRecyclerView.setVisibility(View.VISIBLE);
//                    avLoadingIndicatorView.hide();
//                    if(response.body().getData().size() >0){
//                        mList.addAll(response.body().getData());
//                        if (page == 0){
//                            mHeaderModel = response.body().getNewsHomeHeader();
//                            mNewsMediaAdapter.setmHeaderModel(mHeaderModel);
//                            mNewsMediaAdapter.setmCardViewItem(mList);
//                        }
//                        mNewsMediaAdapter.notifyDataSetChanged();
//
//                    }
//                    if(isRefreshing){
//                        mRefresh.setRefreshing(false);
//                        mScrollListener.reset();
//                    }
//                    mIsRefresh = false;
//                    mDilatingDotsProgressBar.hide();
                }
            }

            @Override
            public void onFailure(Call<NewsHomeSection> call, Throwable t) {
                t.printStackTrace();
                mFragment.showError(page,isRefreshing);
//                mIsRefresh = false;
//                mDilatingDotsProgressBar.hide();
//                mErrorView.setVisibility(View.VISIBLE);
//                avLoadingIndicatorView.setVisibility(View.GONE);
//                if(page == 0 && isRefreshing == false){
//                    mRecyclerView.setVisibility(View.GONE);
//                }
//
//                mRefresh.setRefreshing(false);

               // Toast.makeText(getContext(),"network error!",Toast.LENGTH_LONG).show();
            }

        });
    }

    public void showError(final int page,final boolean isRefreshing){
        AppApplication appApplication = (AppApplication) getActivity().getApplication();
        appApplication.sentEvent(AnalaticEnum.NEWS_PAGE_CATEGORY_EVENT.getValue(),AnalaticEnum.NEWS_PAGE_ACTION_ERROR_EVENT.getValue(),String.valueOf(page));
        mMainActive.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIsRefresh = false;
                mDilatingDotsProgressBar.hide();
                avLoadingIndicatorView.setVisibility(View.GONE);
                if(page == 0){
                    mRecyclerView.setVisibility(View.GONE);
                    mErrorView.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(getContext(),"network error!",Toast.LENGTH_LONG).show();
                }

                mRefresh.setRefreshing(false);
                Toast.makeText(getContext(),"network error!",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void updateData(final int page,final Response<NewsHomeSection> response,final boolean isRefreshing){
        AppApplication appApplication = (AppApplication) getActivity().getApplication();
        appApplication.sentEvent(AnalaticEnum.NEWS_PAGE_CATEGORY_EVENT.getValue(),AnalaticEnum.NEWS_PAGE_ACTION_SUCCESS_EVENT.getValue(),String.valueOf(page));

        mMainActive.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setVisibility(View.VISIBLE);
                avLoadingIndicatorView.hide();
                if(response.body().getData().size() >0){
                    mList.addAll(response.body().getData());
                    if (page == 0){
                        mHeaderModel = response.body().getNewsHomeHeader();
                        mNewsMediaAdapter.setmHeaderModel(mHeaderModel);
                        mNewsMediaAdapter.setmCardViewItem(mList);
                    }
                    mNewsMediaAdapter.notifyDataSetChanged();

                }
                if(isRefreshing){
                    mRefresh.setRefreshing(false);
                    mScrollListener.reset();
                }
                mIsRefresh = false;
                mDilatingDotsProgressBar.hide();
            }
        });

    }
    public void sentEvent(int page){
           AppApplication application = (AppApplication) getActivity().getApplication();
            Tracker t = application.getDefaultTracker();
            t.send(new HitBuilders.EventBuilder()
                    .setCategory("page_news")
                    .setAction("loadPage")
                    .setLabel(String.valueOf(page))
                    .build());

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

    @Override
    public void onResume() {
        super.onResume();
        mFragment = this;
        mMainActive = (MainActivity)getActivity();
    }
}
