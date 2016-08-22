package com.sip.shortnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sip.shortnews.MainActivity;
import com.sip.shortnews.R;

/**
 * Created by ssd on 8/22/16.
 */
public class DetailFragment extends PFragment  {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.social_detail_layout,container,false);
        ImageView close = (ImageView)v.findViewById(R.id.btn_close);
        final MainActivity mainActivity = (MainActivity)getActivity();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onBackPressed();
            }
        });
        return v;
    }
}
