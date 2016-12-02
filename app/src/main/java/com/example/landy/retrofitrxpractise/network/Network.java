package com.example.landy.retrofitrxpractise.network;

import com.example.landy.retrofitrxpractise.network.api.GitHubApi;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by landy on 16/12/2.
 * Network
 */

public class Network {
    private static final String GITHUB_END_POINT = "https://api.github.com/";
    private static GitHubApi gitHubApi;
    private static OkHttpClient okHttpClient;
    private static Converter.Factory gsonConverterFactory;
    private static CallAdapter.Factory rxJavaCallAdapterFactory;
    
    public static GitHubApi getGitHubApi() {
        if (gitHubApi == null) {
            if (okHttpClient == null) {
                gsonConverterFactory = GsonConverterFactory.create();
                rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
                okHttpClient = new OkHttpClient();
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(GITHUB_END_POINT)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            gitHubApi = retrofit.create(GitHubApi.class);
        }
        return gitHubApi;
    }
}
