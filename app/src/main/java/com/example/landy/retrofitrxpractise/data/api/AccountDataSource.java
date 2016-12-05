package com.example.landy.retrofitrxpractise.data.api;

import android.content.Context;

import com.example.landy.retrofitrxpractise.data.model.User;

import rx.Observable;

public interface AccountDataSource {

    Observable<User> login(Context context, String username, String password);
}