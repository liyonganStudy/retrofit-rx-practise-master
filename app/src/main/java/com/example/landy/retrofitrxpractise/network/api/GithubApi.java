package com.example.landy.retrofitrxpractise.network.api;

import com.example.landy.retrofitrxpractise.model.Repo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by landy on 16/12/2.
 * GitHub api
 */

public interface GitHubApi {

    @GET("users/{user}/repos")
    Observable<List<Repo>> getReposOf(@Path("user") String user);
}
