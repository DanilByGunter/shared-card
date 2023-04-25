package com.project.shared_card.model.activity.splash_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.project.shared_card.R;
import com.project.shared_card.model.activity.main_screen.MainActivity;
import com.project.shared_card.model.activity.registration.RegistrationActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        if(settings.getString(getString(R.string.key_for_user_id),"no id").equals("no id")) {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();

    }
}