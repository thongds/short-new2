package com.sip.shortnews.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sip.shortnews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ssd on 8/27/16.
 */
public class CardAdapter extends PagerAdapter {
    private List<CardView> mViews;

    public CardAdapter(){
        mViews = new ArrayList<>();
        for(int i=0;i<10;i++){
            mViews.add(null);
        }
    }
    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.social_detail_layout,container,false);
        CardView cardView = (CardView)v.findViewById(R.id.cardView);
        container.addView(cardView);
        cardView.setMaxCardElevation(18);
        mViews.add(cardView);
        notifyDataSetChanged();
        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }
}
