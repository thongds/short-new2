package com.sip.shortnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sip.shortnews.R;
import com.sip.shortnews.adapter.CardAdapter;

/**
 * Created by ssd on 8/27/16.
 */
public class DetailViewPageFragment extends PFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_view_page_layout,container,false);
        ViewPager viewPager = (ViewPager)v.findViewById(R.id.viewPager);
        CardAdapter cardAdapter = new CardAdapter();
        viewPager.setAdapter(cardAdapter);
        return v;
    }
}
