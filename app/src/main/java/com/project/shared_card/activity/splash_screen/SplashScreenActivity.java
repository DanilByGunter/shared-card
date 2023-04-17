package com.project.shared_card.activity.splash_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.MainActivity;
import com.project.shared_card.activity.registration.UserGroupRegistrationActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        if(settings.getString(getString(R.string.key_for_user_id),"no id").equals("no id")) {
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