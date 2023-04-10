package com.project.shared_card.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.project.shared_card.R;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if(getString(R.string.my_id).equals("no id")) {

            Intent intent = new Intent(this, UserGroupRegistrationActivity.class);
            intent.putExtra(UserGroupRegistrationActivity.TEXT_VIEW_KEY, getString(R.string.choose_an_avatar_for_myself));
            intent.putExtra(UserGroupRegistrationActivity.EDIT_VIEW_KEY, getString(R.string.enter_your_name));
            intent.putExtra(UserGroupRegistrationActivity.SCREEN_REGISTRATION, true);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();

    }
}