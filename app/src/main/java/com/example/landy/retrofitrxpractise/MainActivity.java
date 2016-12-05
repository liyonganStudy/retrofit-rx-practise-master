package com.example.landy.retrofitrxpractise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.landy.retrofitrxpractise.data.pref.AccountPref;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountPref.isLogon(MainActivity.this)) {
                    ProfileActivity.launch(MainActivity.this);
                } else {
                    LoginActivity.launch(MainActivity.this);
                }
            }
        });
    }
}
