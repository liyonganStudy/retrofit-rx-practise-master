package com.example.landy.retrofitrxpractise.data.network;

import android.content.Context;

import com.example.landy.retrofitrxpractise.data.api.AccountDataSource;
import com.example.landy.retrofitrxpractise.data.model.User;
import com.example.landy.retrofitrxpractise.data.network.core.GitHubAuthRetrofit;
import com.example.landy.retrofitrxpractise.data.network.request.CreateAuthorization;
import com.example.landy.retrofitrxpractise.data.network.response.AuthorizationResp;
import com.example.landy.retrofitrxpractise.data.network.service.AccountService;
import com.example.landy.retrofitrxpractise.data.pref.AccountPref;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by landy on 16/12/2.
 *
 */

public class RemoteAccountDataSource implements AccountDataSource {

    private static RemoteAccountDataSource INSTANCE;
    private GitHubAuthRetrofit retrofit;

    private RemoteAccountDataSource() {
    }

    public static RemoteAccountDataSource getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteAccountDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<User> login(final Context context, String username, String password) {
        if (retrofit == null) {
            retrofit = new GitHubAuthRetrofit();
        }
        retrofit.setAuthInfo(username, password);
        final AccountService accountService = retrofit.get().create(AccountService.class);
        CreateAuthorization createAuthorization = new CreateAuthorization();
        createAuthorization.note = "MyGitHub";
        createAuthorization.client_id = "aeef2764411d2c3bc6e4";
        createAuthorization.client_secret = "85cbf1862b1b55ea88440cf146dd1d0f3f64c9f7";
        createAuthorization.scopes = new String[]{"user", "repo", "notifications", "gist", "admin:org"};

        return accountService.createAuthorization(createAuthorization)
                .flatMap(new Func1<AuthorizationResp, Observable<User>>() {
                    @Override
                    public Observable<User> call(AuthorizationResp authorizationResp) {
                        String token = authorizationResp.getToken();
                        AccountPref.saveLoginToken(context, token);
                        return accountService.getUserInfo(token);
                    }
                });
    }
}
