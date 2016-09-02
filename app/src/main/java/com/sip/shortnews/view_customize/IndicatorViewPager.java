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
    private int mNumberImageVisible;
    private int mMainOldPos = 0;
    private int mIndicatorOldPos = 0;
    public IndicatorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setMainSlide(ViewPager viewPager){
        mMainSlide = viewPager;
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
        mNumberImageVisible = numberImageVisible;
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
                mMainOldPos+=Math.abs(position-mIndicatorOldPos);
            }else{
                //move left
                mMainOldPos-=Math.abs(position-mIndicatorOldPos);
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
                if(isShouldMoveIndicate(mNumberImageVisible,position,mIndicatorOldPos))
                    UtiliFunction.moveStatic(mSubSilde,indicatorPageChangeListener,++mIndicatorOldPos);
            }else{
                if(isShouldMoveIndicate(mNumberImageVisible,position,mIndicatorOldPos))
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
        mMainOldPos = position;
        UtiliFunction.moveStatic(mMainSlide,mainPageChangeListener,position);
    }

}
