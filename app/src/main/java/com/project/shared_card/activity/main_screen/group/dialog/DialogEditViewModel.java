package com.project.shared_card.activity.main_screen.group.dialog;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.project.shared_card.R;
import com.project.shared_card.activity.BasicApp;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group_name.AllGroups;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;
import com.project.shared_card.database.entity.user_name.UserNameEntity;

import java.util.List;

public class DialogEditViewModel extends AndroidViewModel {

    private final ImplDB implDB;
    private final SharedPreferences settings;
    private String idUser;

    public DialogEditViewModel(@NonNull Application application) {
        super(application);
        implDB = ((BasicApp) application).getRepository();
        settings = application.getSharedPreferences(application.getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        idUser = settings.getString(getApplication().getString(R.string.key_for_me_id_server), "no_id");

    }

    public void save(Drawable image, String name){
        byte[] picture;
        if (image==null){
            picture = DbBitmapUtility.getBytes(((BitmapDrawable) getApplication().getDrawable(R.drawable.defaul_avatar)).getBitmap());
        }
        else {
            picture = DbBitmapUtility.getBytes(((BitmapDrawable) image.getCurrent()).getBitmap());
        }
        implDB.user_name().updateMe(name,picture);
        implDB.group_name().updateMe(name,picture);
        if(!idUser.equals("no_id"))
            implDB.user_name().update(new UserNameEntity(Long.valueOf(idUser),name,picture));
        
    }
}
