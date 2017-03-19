package com.sip.shortnews.service.home_api;

import com.sip.shortnews.config.ServerConfig;
import com.sip.shortnews.model.NewsHomeItem;
import com.sip.shortnews.model.NewsHomeSection;
import com.sip.shortnews.model.SocialMediaItem;
import com.sip.shortnews.model.SocialMediaSection;
import com.sip.shortnews.model.SupportResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ssd on 8/27/16.
 */
public class HomeMediaService {

    static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();

    public static Service service (){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.102/ShortNews_Server/public/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        return  service;
    }

    public interface Service {
        @FormUrlEncoded
        @POST("api/home/check-version")
        Call<SupportResponse> checkVersion(@Field("version") String version,@Field("platform") String platform);
        @GET("api/news/getnews")
        Call<NewsHomeSection> getNews(@Query("page") int page);
        @GET("api/news/get-social")
        Call<SocialMediaSection> getSocial(@Query("page") int page);
    }

}
