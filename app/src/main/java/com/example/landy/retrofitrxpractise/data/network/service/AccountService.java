package com.example.landy.retrofitrxpractise.data.network.service;

import com.example.landy.retrofitrxpractise.data.model.User;
import com.example.landy.retrofitrxpractise.data.network.request.CreateAuthorization;
import com.example.landy.retrofitrxpractise.data.network.response.AuthorizationResp;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by landy on 16/12/3.
 *
 */

public interface AccountService {

    @POST("/authorizations")
    Observable<AuthorizationResp> createAuthorization(@Body CreateAuthorization createAuthorization);

    @GET("/user")
    Observable<User> getUserInfo(@Query("access_token") String token);

}
