package com.master.newsapi.Retrofit;

import com.master.newsapi.Api.NewsApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit=null;
    private static String BASE_URL="https://newsapi.org/";


    public static Retrofit getService(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
