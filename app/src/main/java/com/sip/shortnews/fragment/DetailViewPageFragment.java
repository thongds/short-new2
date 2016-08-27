package com.sip.shortnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sip.shortnews.MainActivity;
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

        final MainActivity mainActivity = (MainActivity) getActivity();
        LinearLayout linearLayout1 = (LinearLayout)v.findViewById(R.id.ll_close1);
        LinearLayout linearLayout2 = (LinearLayout)v.findViewById(R.id.ll_close2);
        CardAdapter cardAdapter = new CardAdapter(getContext());
        viewPager.setAdapter(cardAdapter);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onBackPressed();
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onBackPressed();
            }
        });
        return v;
    }

}
