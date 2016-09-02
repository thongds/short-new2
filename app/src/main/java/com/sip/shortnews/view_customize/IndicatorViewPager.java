package com.sip.shortnews.view_customize;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sip.shortnews.Utilies.UtiliFunction;

/**
 * Created by ssd on 8/30/16.
 */
public class IndicatorViewPager extends ViewPager implements IndicatorAdapter.IFitemIndicatorClick {
    private String TAG = this.getClass().getSimpleName();
    private  ViewPager mMainSlide;
    private ViewPager mSubSilde;
    private int mParentWidth;
    private int mNumberImageVisible;
    private int mIndicatorItemWidth;
    private int mIndicatorItemHeight;
    private int mMainOldPos = 0;
    private int mCurrentPosMain =0;
    private int mCurrentPosBoth = 0;
    private int mIndicatorOldPos = 0;
    private  int mCurrentPosIndicator =0;
    private  boolean isItemClick = false;
    private int visiblePage = 3;
    public IndicatorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setMainSlide(ViewPager viewPager){
        mMainSlide = viewPager;
        View v = (View)viewPager.getParent();
        mParentWidth = v.getWidth();
        mSubSilde = this;
        MainSliderAdapter mainSliderAdapter =(MainSliderAdapter)mMainSlide.getAdapter();
        IndicatorAdapter indicatorAdapter = new IndicatorAdapter(mainSliderAdapter.getUrl(),mNumberImageVisible,this);
        this.setAdapter(indicatorAdapter);
        this.setClipToPadding(false);
        mMainSlide.addOnPageChangeListener(mainPageChangeListener);
        mSubSilde.addOnPageChangeListener(indicatorPageChangeListener);
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
        mIndicatorItemHeight = this.getLayoutParams().width;
        mNumberImageVisible = numberImageVisible;
        mIndicatorItemWidth = numberImageVisible/mIndicatorItemHeight;
        this.setOffscreenPageLimit(8);
        return this;
    }
    IndicatorPageChangeListener indicatorPageChangeListener = new IndicatorPageChangeListener() {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(mIndicatorOldPos < position){
                //move right
                ++mMainOldPos;
            }else{
                //move left
                --mMainOldPos;
            }
            UtiliFunction.moveStatic(mMainSlide,mainPageChangeListener,mMainOldPos);
            mIndicatorOldPos = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    IndicatorPageChangeListener mainPageChangeListener = new IndicatorPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(mMainOldPos< position){
                if(isShouldMoveIndicate(visiblePage,position,mIndicatorOldPos))
                    UtiliFunction.moveStatic(mSubSilde,indicatorPageChangeListener,++mIndicatorOldPos);
            }else{
                if(isShouldMoveIndicate(visiblePage,position,mIndicatorOldPos))
                    UtiliFunction.moveStatic(mSubSilde,indicatorPageChangeListener,--mIndicatorOldPos);
            }
            mMainOldPos = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    @Override
    public void itemClick(int position) {
        isItemClick = true;
        mMainOldPos = position;
        UtiliFunction.moveStatic(mMainSlide,mainPageChangeListener,position);
    }

}
