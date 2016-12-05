package com.example.landy.retrofitrxpractise.data;

import android.content.Context;

import com.example.landy.retrofitrxpractise.data.api.SimpleCallback;
import com.example.landy.retrofitrxpractise.data.model.User;
import com.example.landy.retrofitrxpractise.data.network.RemoteAccountDataSource;
import com.example.landy.retrofitrxpractise.data.pref.AccountPref;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by landy on 16/12/3.
 *
 */

public class DataRepository {

    private static DataRepository INSTANCE;

    public static DataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository();
        }
        return INSTANCE;
    }

    private <T> Observable.Transformer<T, T> applyCommonTransform(final SimpleCallback<T> simpleCallback) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                if (simpleCallback != null) {
                                    simpleCallback.onPrepare();
                                }
                            }
                        })
                        .doOnTerminate(new Action0() {
                            @Override
                            public void call() {
                                if (simpleCallback != null) {
                                    simpleCallback.onFinish();
                                }
                            }
                        });
            }
        };
    }

    private static class CommonObserver<T> implements Observer<T> {

        private SimpleCallback<T> simpleCallback;

        public CommonObserver(SimpleCallback<T> simpleCallback) {
            this.simpleCallback = simpleCallback;
        }

        @Override
        public void onCompleted() {
            if (simpleCallback != null) {
                simpleCallback.onComplete();
            }
            onRealCompleted();
        }

        protected void onRealCompleted() {}

        @Override
        public void onError(Throwable e) {
            if (simpleCallback != null) {
                simpleCallback.onError(e);
            }
            onRealError(e);
        }

        protected void onRealError(Throwable e) {}

        @Override
        public void onNext(T t) {
            if (simpleCallback != null) {
                simpleCallback.onNext(t);
            }
            onRealNext(t);
        }

        protected void onRealNext(T t) {}
    }

    public Subscription login(final Context context, String username, String password, final SimpleCallback<User> simpleCallback) {
        return RemoteAccountDataSource.getINSTANCE().login(context, username, password)
                .compose(applyCommonTransform(simpleCallback))
                .subscribe(new CommonObserver<User>(simpleCallback) {
                    @Override
                    protected void onRealNext(User user) {
                        super.onRealNext(user);
                        AccountPref.saveLogonUser(context, user);
                    }
                });
    }
}
