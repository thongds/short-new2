package com.sip.shortnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sip.shortnews.fragment.NewsFragment;
import com.sip.shortnews.fragment.SocialFragment;
import com.sip.shortnews.model.SocialMediaItem;

/**
 * Created by ssd on 8/21/16.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        if(position ==1){
//            return new SocialFragment();
//        }
        return new NewsFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        if(position ==1)
//            return "Mạng Xã Hội";
        return "Báo Chí";
    }

    @Override
    public int getCount() {
        return 1;
    }
}
