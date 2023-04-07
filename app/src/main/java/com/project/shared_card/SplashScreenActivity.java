package com.project.shared_card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashScreenActivity extends AppCompatActivity {
//rdddrsdfdsd//ddddd
    //dsdsdadasdsasad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this,UserGroupRegistrationActivity.class);
        startActivity(intent);
        finish();

    }
}