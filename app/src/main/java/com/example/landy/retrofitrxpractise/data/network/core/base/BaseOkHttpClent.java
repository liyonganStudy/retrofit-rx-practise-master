package com.example.landy.retrofitrxpractise.data.network.core.base;

import com.example.landy.retrofitrxpractise.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;

/**
 * Created by landy on 16/12/2.
 *
 */

public abstract class BaseOkHttpClent {
    private static final long TIMEOUT_CONNECT = 30 * 1000;

    public OkHttpClient get() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? Level.BODY : Level.NONE);

        builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor);
        builder = customize(builder);
        return builder.build();
    }

    public abstract OkHttpClient.Builder customize(OkHttpClient.Builder builder);
}
