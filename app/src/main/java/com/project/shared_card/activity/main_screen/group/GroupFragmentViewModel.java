package com.project.shared_card.activity.main_screen.group;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.project.shared_card.R;
import com.project.shared_card.activity.BasicApp;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group.GroupEntity;
import com.project.shared_card.database.entity.group_name.AllGroups;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;

import java.util.List;

public class GroupFragmentViewModel extends AndroidViewModel {
    private final ImplDB implDB;
    private final LiveData<GroupNameEntity> group;
    private final LiveData<List<AllGroups>> allGroups;
    private final SharedPreferences settings;
    private String groupId;

    public GroupFragmentViewModel(@NonNull Application application) {
        super(application);
        implDB = ((BasicApp) application).getRepository();
        settings = application.getSharedPreferences(application.getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        groupId = settings.getString(getApplication().getString(R.string.key_for_select_group_id), "no_id");

        group = implDB.group_name().getGroupById(Long.valueOf(groupId));

        allGroups = implDB.group_name().getAllGroups();
    }

    public LiveData<GroupNameEntity> getGroup() {
        return group;
    }

    public LiveData<List<AllGroups>> getAllGroups() {
        return allGroups;
    }
    public void update(Long id, String name, Drawable image) {
        byte[] photo;
        if (image == null) {
            photo = DbBitmapUtility.getBytes(((BitmapDrawable) getApplication().getDrawable(R.drawable.defaul_avatar)).getBitmap());
        } else {
            photo = DbBitmapUtility.getBytes(((BitmapDrawable) image.getCurrent()).getBitmap());
        }

        implDB.group_name().updateForId(id,name,photo);
    }
}
