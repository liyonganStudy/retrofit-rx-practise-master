package com.example.landy.retrofitrxpractise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.landy.retrofitrxpractise.data.DataRepository;
import com.example.landy.retrofitrxpractise.data.api.SimpleCallback;
import com.example.landy.retrofitrxpractise.data.model.User;

import rx.subscriptions.CompositeSubscription;
import utils.UIHelper;

import static utils.UIHelper.toast;

public class LoginActivity extends AppCompatActivity {

    private EditText password;
    private EditText userName;
    private Button login;
    private boolean logining = false;
    private CompositeSubscription compositeSubscription;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        compositeSubscription = new CompositeSubscription();

        password = (EditText) findViewById(R.id.password);
        userName = (EditText) findViewById(R.id.username);
        login = (Button) findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logining) {
                    return;
                }
                String thePassword = password.getText().toString();
                String theUserName = userName.getText().toString();
                if (TextUtils.isEmpty(thePassword)) {
                    toast(LoginActivity.this, "密码不能为空");
                } else if (TextUtils.isEmpty(theUserName)) {
                    toast(LoginActivity.this, "用户名不能为空");
                } else {
                    compositeSubscription.add(DataRepository.getInstance().login(LoginActivity.this, theUserName, thePassword, new SimpleCallback<User>() {
                        @Override
                        public void onPrepare() {
                            super.onPrepare();
                            login.setText(getResources().getString(R.string.logining));
                            logining = true;
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            login.setText(getResources().getString(R.string.login));
                            logining = false;
                            ProfileActivity.launch(LoginActivity.this);
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void onNext(User result) {
                            super.onNext(result);
                            UIHelper.toast(LoginActivity.this, result.toString());
                        }
                    }));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.clear();
    }
}
