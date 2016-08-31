package com.sip.shortnews.view_customize;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sip.shortnews.R;

import java.util.List;

/**
 * Created by ssd on 8/30/16.
 */
public class IndicatorAdapter extends PagerAdapter {
    private int mItemWidth;
    private  int mItemHeigh;
    private String[] mUrlArray;
    private IFitemIndicatorClick mIFitemIndicatorClick;
    public  IndicatorAdapter (String[] urlArray,int itemWidth,int itemHeigh,IFitemIndicatorClick iFitemIndicatorClick){
        mUrlArray = urlArray;
        mItemWidth=itemWidth;
        mItemHeigh = itemHeigh;
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
        ImageLoader.getInstance().displayImage(mUrlArray[position],imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIFitemIndicatorClick.itemClick(position);
            }
        });
        container.addView(card);
        return v;
    }
    @Override
    public float getPageWidth(int position) {
        return 0.3f;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }



    public interface IFitemIndicatorClick{
        void itemClick(int position);
    }
}
