package com.example.landy.retrofitrxpractise.data.network.core;

import android.text.TextUtils;
import android.util.Base64;

import com.example.landy.retrofitrxpractise.data.network.core.base.BaseOkHttpClent;
import com.example.landy.retrofitrxpractise.data.network.core.base.BaseRetrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by landy on 16/12/2.
 *
 */

public class GitHubAuthRetrofit extends BaseRetrofit {

    private static final String END_POINT = "https://api.github.com/";

    private String userName;
    private String password;

    @Override
    public String getApiEndpoint() {
        return END_POINT;
    }

    public void setAuthInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public OkHttpClient getHttpClient() {
        return new AuthHttpClient(userName, password).get();
    }

    private static class AuthHttpClient extends BaseOkHttpClent {

        private String userName;
        private String password;

        public AuthHttpClient(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        @Override
        public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {
            if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
                builder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        String userCredentials = userName + ":" + password;

                        String basicAuth = "Basic " + new String(Base64.encode(userCredentials.getBytes(), Base64.DEFAULT));
                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization" , basicAuth.trim());
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });
            }
            return builder;
        }
    }
}
