package com.sip.shortnews.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.sip.shortnews.R;
import com.sip.shortnews.adapter.NewsMediaAdapter;
import com.sip.shortnews.model.CardViewItem;

import java.util.ArrayList;

/**
 * Created by ssd on 8/14/16.
 */
public class NewsFragment extends PFragment {
    private LinearLayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_medial_holder_layout,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        ArrayList<CardViewItem> cardViewItemsArray = new ArrayList<>();
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.vnexpress_photo);
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ImageView imageViewPost = new ImageView(getContext());
        imageViewPost.setImageResource(R.drawable.vnexpress_post);
        BitmapDrawable drawablePost = (BitmapDrawable) imageViewPost.getDrawable();
        Bitmap bitmapPost = drawablePost.getBitmap();
        String content = getString(R.string.vnexpress_content);
        String title = getString(R.string.vnexpress_title);
        for (int i = 0 ; i<1000; i++){
            CardViewItem cardViewItem = new CardViewItem();
            cardViewItem.setmPageLogo(bitmap);
            cardViewItem.setmImagePost(bitmapPost);
            cardViewItem.setmTagColor("#984A53");
            cardViewItem.setmTitleColor("#984A53");
            cardViewItem.setVideo(true);
            cardViewItem.setmPostContent(content);
            cardViewItem.setmPostTitle(title);
            cardViewItemsArray.add(cardViewItem);
        }
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        NewsMediaAdapter newsMediaAdapter = new NewsMediaAdapter(cardViewItemsArray);
        recyclerView.setAdapter(newsMediaAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
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
