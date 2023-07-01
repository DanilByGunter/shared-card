package com.project.shared_card.activity.main_screen.group.dialog;

import android.app.Application;
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
import com.project.shared_card.activity.converter.ModelConverter;
import com.project.shared_card.database.ImplDB;
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
import com.project.shared_card.retrofit.model.dto.UsersGroup;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogGroupJoinViewModel extends AndroidViewModel {

    private final ImplDB implDB;
    private final SharedPreferences settings;
    private final SharedPreferences.Editor editor;
    private String id_user;
    private final RetrofitService server;
    private final GroupIdApi groupIdApi;
    private final UserApi userApi;
    private final TheAllGroupApi theAllGroupApi;

    public DialogGroupJoinViewModel(@NonNull Application application) {
        super(application);
        server = new RetrofitService();
        implDB = ((BasicApp) application).getRepository();
        settings = application.getSharedPreferences(application.getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        editor = settings.edit();
        groupIdApi = server.getRetrofit().create(GroupIdApi.class);
        userApi = server.getRetrofit().create(UserApi.class);
        theAllGroupApi = server.getRetrofit().create(TheAllGroupApi.class);

        id_user = settings.getString(getApplication().getString(R.string.key_for_me_id_server),"no_id");
        if(id_user.equals("no_id"))
            getIdUser();

    }
    private void getIdUser(){
        implDB.user_name().getMe().observe((LifecycleOwner) this, entity -> {
            userApi.addUser(new User(entity.getName(),entity.getPhoto())).enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {
                    id_user= String.valueOf(response.body());
                    editor.putString(getApplication().getString(R.string.key_for_me_id_server), response.body().toString()).apply();
                    implDB.user_name().createUser(new UserNameEntity(response.body(),entity.getName(),entity.getPhoto()));
                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {

                }
            });
        });
    }


    public void addUserInGroup(long idGroup,String nameGroup) {
        if(id_user.equals("no_id")){
            Toast toast = Toast.makeText(getApplication(),"пользователь не создан",Toast.LENGTH_SHORT);
            toast.show();
        }else{
            UserWithGroup userWithGroup = new UserWithGroup(idGroup,nameGroup,
                    new TheGroupId(Long.parseLong(id_user),0));
            groupIdApi.saveUser(userWithGroup).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    getGroup(idGroup);
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast toast = Toast.makeText(getApplication(),"группы не существует",Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }

    void getGroup(long idGroup){
        theAllGroupApi.getGroupById(idGroup).enqueue(new Callback<TheAllGroup>() {
            @Override
            public void onResponse(Call<TheAllGroup> call, Response<TheAllGroup> response) {
                GroupNameEntity entity = ModelConverter.FromServerGroupToEntity(response.body(),
                        String.valueOf(getApplication().getFilesDir()));
                implDB.group_name().createGroup(entity);
            }

            @Override
            public void onFailure(Call<TheAllGroup> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(),"Нет доступа к серверу",Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        groupIdApi.getAllUsers(idGroup).enqueue(new Callback<List<UsersGroup>>() {
            @Override
            public void onResponse(Call<List<UsersGroup>> call, Response<List<UsersGroup>> response) {
                List<UsersGroup> usersGroups = response.body();
                List<UserNameEntity> users = ModelConverter.FromServerUserToEntity(usersGroups, String.valueOf(getApplication().getFilesDir()));
                List<Integer> statuses = usersGroups.stream().map(UsersGroup::getStatus).collect(Collectors.toList());
                implDB.user_name().createUsers(users);
                implDB.group().createGroupFromList(idGroup,users,statuses);
            }

            @Override
            public void onFailure(Call<List<UsersGroup>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplication(),"Нет доступа к серверу",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
