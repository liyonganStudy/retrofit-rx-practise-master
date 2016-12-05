package com.example.landy.retrofitrxpractise.data.network.core.base;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by landy on 16/12/2.
 * base retrofit
 */

public abstract class BaseRetrofit {

    public Retrofit get() {
        return new Retrofit.Builder()
                .baseUrl(getApiEndpoint())
                .client(getHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public abstract String getApiEndpoint();

    public abstract OkHttpClient getHttpClient();
}
