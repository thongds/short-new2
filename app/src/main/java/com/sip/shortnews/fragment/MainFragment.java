package com.sip.shortnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sip.shortnews.MainActivity;
import com.sip.shortnews.R;

/**
 * Created by ssd on 8/13/16.
 */
public class MainFragment extends PFragment {
    private Button btn1;
    private MainActivity mMainActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);
        btn1 =(Button)view.findViewById(R.id.btn_beginner);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsFragment contentFragment = new NewsFragment();
                mMainActivity.replaceForground(contentFragment);
                mMainActivity.changeNavigateMenu(false);

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMainActivity =(MainActivity)getActivity();
    }
}
