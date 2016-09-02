package com.sip.shortnews.view_customize;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sip.shortnews.R;

import java.util.List;

/**
 * Created by ssd on 8/30/16.
 */
public class IndicatorAdapter extends PagerAdapter {
    String TAG = this.getClass().getSimpleName();
    private int mNumberPageVisisble;
    private String[] mUrlArray;
    private int oldPosition =-1;

    public int getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(int newPosition) {
        this.newPosition = newPosition;
    }

    public int getOldPosition() {
        return oldPosition;
    }

    public void setOldPosition(int oldPosition) {
        this.oldPosition = oldPosition;
    }

    private int newPosition;
    private IFitemIndicatorClick mIFitemIndicatorClick;
    public  IndicatorAdapter (String[] urlArray,int numberPageVisible,IFitemIndicatorClick iFitemIndicatorClick){
        mUrlArray = urlArray;
        mNumberPageVisisble = numberPageVisible;
        mIFitemIndicatorClick = iFitemIndicatorClick;
    }

    @Override
    public int getCount() {
        return mUrlArray.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.indicator_card,container,false);
        CardView card = (CardView)v.findViewById(R.id.cardView);
//        card.setMaxCardElevation(3.0f);
        ImageView imageView = (ImageView)v.findViewById(R.id.image);
       // final LinearLayout mask = (LinearLayout) v.findViewById(R.id.mask);
        ImageLoader.getInstance().displayImage(mUrlArray[position],imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIFitemIndicatorClick.itemClick(position);
               // mask.setVisibility(View.INVISIBLE);
            }
        });
        container.addView(card,position);
        return v;
    }


    @Override
    public void startUpdate(ViewGroup container) {
        Log.e(TAG,"startUpdate");

        CardView cardViewMask = (CardView) container.getChildAt(oldPosition);
        if(cardViewMask!=null) {
            LinearLayout mask = (LinearLayout) cardViewMask.getChildAt(1);
            if (mask != null)
                mask.setBackgroundColor(Color.parseColor("#80F5F9FA"));
        }
        CardView cardViewLight = (CardView) container.getChildAt(newPosition);
        if(cardViewLight!=null) {
            LinearLayout mask = (LinearLayout) cardViewLight.getChildAt(1);
            if (mask != null)
                mask.setBackgroundColor(Color.TRANSPARENT);
        }
        super.startUpdate(container);
    }

    @Override
    public float getPageWidth(int position) {
        return (float)1/mNumberPageVisisble;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    public interface IFitemIndicatorClick{
        void itemClick(int position);
    }


}
