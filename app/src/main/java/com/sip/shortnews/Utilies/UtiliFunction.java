package com.sip.shortnews.Utilies;

import android.content.Context;
import android.support.v4.view.ViewPager;

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
    public static void moveStatic(ViewPager viewPager, ViewPager.OnPageChangeListener listener, int moveTo){
        viewPager.removeOnPageChangeListener(listener);
        viewPager.setCurrentItem(moveTo);
        viewPager.addOnPageChangeListener(listener);
    }
}
