package com.project.shared_card.activity.main_screen;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.project.shared_card.R;
import com.project.shared_card.activity.BasicApp;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;

public class MainActivityViewModel extends AndroidViewModel {

    private final ImplDB implDB;
    private final SharedPreferences settings;
    private final String groupId;
    private final LiveData<GroupNameEntity> group;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        settings = application.getSharedPreferences(application.getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        groupId = settings.getString(getApplication().getString(R.string.key_for_select_group_id), "no_id");
        implDB =  ((BasicApp) application).getRepository();

        group = implDB.group_name().getGroupById(Long.valueOf(groupId));
    }

    public LiveData<GroupNameEntity> getGroup() {
        return group;
    }
}
