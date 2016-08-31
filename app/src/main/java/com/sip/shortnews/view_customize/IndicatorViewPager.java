package com.sip.shortnews.view_customize;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by ssd on 8/30/16.
 */
public class IndicatorViewPager extends ViewPager implements IndicatorAdapter.IFitemIndicatorClick {
    private  ViewPager mMainSlide;
    private ViewPager mSubSilde;
    private int mParentWidth;
    private int mNumberImageVisible;
    private int mIndicatorItemWidth;
    private int mIndicatorItemHeight;
    private int mVirtualPos;
    private int oldPos = 0;
    private int mCurrentPosBoth;
    private  boolean isItemClick = false;
    public IndicatorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setMainSlide(ViewPager viewPager){
        mMainSlide = viewPager;
        View v = (View)viewPager.getParent();
        mParentWidth = v.getWidth();
        mSubSilde = this;
        MainSliderAdapter mainSliderAdapter =(MainSliderAdapter)mMainSlide.getAdapter();
        IndicatorAdapter indicatorAdapter = new IndicatorAdapter(mainSliderAdapter.getUrl(),mIndicatorItemWidth,mIndicatorItemHeight,this);
        this.setAdapter(indicatorAdapter);
        this.setClipToPadding(false);
        mMainSlide.addOnPageChangeListener(pageChangeListener);
        mSubSilde.addOnPageChangeListener(pageChangeListener);
    }

    @Override
    public int getPageMargin() {
        return super.getPageMargin();
    }

    /**
    * Set indicator height and set number of image visible on screen
     * @param numberImageVisible number of image visible on screen
    * */

    public  IndicatorViewPager setImageVisible(int numberImageVisible){
        mIndicatorItemHeight = this.getLayoutParams().height;
        mNumberImageVisible = numberImageVisible;
        mIndicatorItemWidth = numberImageVisible/mIndicatorItemHeight;
        this.setOffscreenPageLimit(8);
        return this;
    }
    OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            mSubSilde.setCurrentItem(position,true);
//            mMainSlide.setCurrentItem(position);
        }

        @Override
        public void onPageSelected(int position) {
            Log.e("--position--","position "+position);

            if(!isItemClick)
                mSubSilde.setCurrentItem(position,true);
            mMainSlide.setCurrentItem(position);
            isItemClick = false;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void itemClick(int position) {
        isItemClick = true;
        mMainSlide.setCurrentItem(position);
    }
}
