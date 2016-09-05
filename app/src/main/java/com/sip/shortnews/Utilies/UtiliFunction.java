package com.sip.shortnews.Utilies;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;

/**
 * Created by ssd on 8/14/16.
 */
public  class UtiliFunction {
    public static  float dpTopixcel(Context context,int dp){
        return dp * context.getResources().getDisplayMetrics().density;
    }
    public static int dpTopxInt(Context context,int dp){
        return (int)( dpTopixcel(context,dp) +0.5f );
    }
    public static void moveStatic(final ViewPager viewPager, final ViewPager.OnPageChangeListener listener, final int moveTo){
        viewPager.removeOnPageChangeListener(listener);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               // if(position == moveTo)

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_IDLE)
                    viewPager.addOnPageChangeListener(listener);
            }
        });
        viewPager.setCurrentItem(moveTo);
    }
}
