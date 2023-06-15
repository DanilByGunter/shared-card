package com.project.shared_card.activity;

import android.app.Application;

import com.project.shared_card.database.ImplDB;

public class BasicApp extends Application {
    public void onCreate() {
        super.onCreate();
    }

    public ImplDB getRepository() {
        return ImplDB.getInstance(this);
    }
}
