package com.sip.shortnews.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sip.shortnews.R;
import com.sip.shortnews.adapter.SocialMediaAdapter;
import com.sip.shortnews.model.SocialMediaItem;

import java.util.ArrayList;

/**
 * Created by ssd on 8/20/16.
 */
public class SocialFragment extends PFragment {
    private LinearLayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social_holder_layout,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        ArrayList<SocialMediaItem> socialViewItemsArray = new ArrayList<>();
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.fanpage_logo);
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ImageView imageViewPost = new ImageView(getContext());
        imageViewPost.setImageResource(R.drawable.youtube_post);
        BitmapDrawable drawablePost = (BitmapDrawable) imageViewPost.getDrawable();
        Bitmap bitmapPost = drawablePost.getBitmap();

        ImageView socialLogo = new ImageView(getContext());
        socialLogo.setImageResource(R.drawable.youtube_logo);
        BitmapDrawable drawableSocialLogo = (BitmapDrawable) socialLogo.getDrawable();
        Bitmap bitmapSocialLogo = drawableSocialLogo.getBitmap();

        String content = "Tập 252 : Uống 2 ly Beer LU bị phạt 17 triệu";
        for (int i = 0 ; i<1000; i++){
            SocialMediaItem socialItem = new SocialMediaItem();
            socialItem.setYoutube(true);
            socialItem.setmSocialLogo(bitmapSocialLogo);
            socialItem.setmPageLogo(bitmap);
            socialItem.setmImagePost(bitmapPost);
            socialItem.setmTagColor("#AB192B");
            socialItem.setmTitleColor("#000000");
            socialItem.setmPageName("Thánh Lồng Tiếng Official");
            socialItem.setVideo(true);
            socialItem.setmPostContent(content);
            socialViewItemsArray.add(socialItem);
        }
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        SocialMediaAdapter socialMediaAdapter = new SocialMediaAdapter(socialViewItemsArray);
        recyclerView.setAdapter(socialMediaAdapter);
        return view;
    }
}
