package com.example.landy.retrofitrxpractise;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.landy.retrofitrxpractise.data.pref.AccountPref;

public class ProfileActivity extends AppCompatActivity {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ProfileActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefile);
        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountPref.removeLogonUser(ProfileActivity.this);
                finish();
            }
        });
    }
}
