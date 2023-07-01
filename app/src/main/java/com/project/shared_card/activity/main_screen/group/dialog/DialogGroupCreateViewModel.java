package com.project.shared_card.activity.main_screen.group.dialog;

import android.app.Application;
import android.app.LocaleManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.project.shared_card.R;
import com.project.shared_card.activity.BasicApp;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group.GroupEntity;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;
import com.project.shared_card.database.entity.user_name.UserNameEntity;
import com.project.shared_card.retrofit.RetrofitService;
import com.project.shared_card.retrofit.api.GroupIdApi;
import com.project.shared_card.retrofit.api.TheAllGroupApi;
import com.project.shared_card.retrofit.api.UserApi;
import com.project.shared_card.retrofit.model.TheAllGroup;
import com.project.shared_card.retrofit.model.TheGroupId;
import com.project.shared_card.retrofit.model.User;
import com.project.shared_card.retrofit.model.dto.UserWithGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogGroupCreateViewModel extends AndroidViewModel {

    private final ImplDB implDB;
    private final SharedPreferences settings;
    private final SharedPreferences.Editor editor;
    private String id_user;
    RetrofitService server;
    UserApi userApi;
    TheAllGroupApi theAllGroupApi;
    GroupIdApi groupIdApi;

    public DialogGroupCreateViewModel(@NonNull Application application) {
        super(application);
        server = new RetrofitService();
        implDB = ((BasicApp) application).getRepository();
        settings = application.getSharedPreferences(application.getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        editor = settings.edit();
        groupIdApi = server.getRetrofit().create(GroupIdApi.class);
        userApi = server.getRetrofit().create(UserApi.class);
        theAllGroupApi = server.getRetrofit().create(TheAllGroupApi.class);

        id_user = settings.getString(getApplication().getString(R.string.key_for_me_id_server), "no_id");
//        if (id_user.equals("no_id"))
//            getIdUser();
    }

    private void getIdUser() {
        implDB.user_name().getMe().observe((LifecycleOwner) this, new Observer<UserNameEntity>() {
            @Override
            public void onChanged(UserNameEntity entity) {
                System.out.println();
            }
        });
//        implDB.user_name().getMe().observe((LifecycleOwner) this, entity -> {
//            userApi.addUser(new User(entity.getName(), entity.getPhoto())).enqueue(new Callback<Long>() {
//                @Override
//                public void onResponse(Call<Long> call, Response<Long> response) {
//                    id_user = String.valueOf(response.body());
//                    editor.putString(getApplication().getString(R.string.key_for_me_id_server), response.body().toString()).apply();
//                    implDB.user_name().createUser(new UserNameEntity(response.body(), entity.getName(), entity.getPhoto()));
//                }
//
//                @Override
//                public void onFailure(Call<Long> call, Throwable t) {
//
//                }
//            });
//        });
    }

    public void createGroup(Drawable image, String name) {
        if (!id_user.equals("no_id")) {
            byte[] photo;
            if (image == null) {
                photo = DbBitmapUtility.getBytes(((BitmapDrawable) getApplication().getDrawable(R.drawable.defaul_avatar)).getBitmap());
            } else {
                photo = DbBitmapUtility.getBytes(((BitmapDrawable) image.getCurrent()).getBitmap());
            }
            createGroup(name, photo);
        }
    }
    private void createGroup(String name,byte[] photo){
        TheAllGroup group = new TheAllGroup();
        group.setName(name);
        group.setPhoto(photo);

        theAllGroupApi.save(group).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                implDB.group_name().createGroup(new GroupNameEntity(response.body(),name,photo));
                saveUser(name,response.body());
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(),"Нет доступа к серверу",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void saveUser(String name,Long id){
        UserWithGroup user = new UserWithGroup();
        user.setId_group(id);
        user.setName_group(name);
        user.setUser(new TheGroupId(Long.valueOf(id_user),1));

        groupIdApi.saveUser(user).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.body()){
                    Toast toast = Toast.makeText(getApplication(),"ошибка создания группы",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    implDB.group().createGroup(new GroupEntity(Long.valueOf(id_user), id, true));
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(),"Нет доступа к серверу",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

}
