package com.sip.shortnews.service.home_api;

import com.sip.shortnews.model.NewsHomeItem;
import com.sip.shortnews.model.SocialMediaItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ssd on 8/27/16.
 */
public class HomeMediaService {


    public static Service service (){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.101/blog/public/").addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        return  service;
    }

    public interface Service {
        @GET("api/news/getnews")
        Call<List<NewsHomeItem>> getNews();
        @GET("api/news/getsocial")
        Call<List<SocialMediaItem>> getSocial();
    }

}