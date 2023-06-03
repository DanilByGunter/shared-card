package com.project.shared_card.activity.main_screen.group;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.activity.converter.ModelConverter;
import com.project.shared_card.activity.main_screen.group.dialog.DialogEdit;
import com.project.shared_card.activity.main_screen.group.dialog.DialogGroupJoin;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group.GroupEntity;
import com.project.shared_card.database.entity.group_name.AllGroups;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupFragment extends Fragment {
    private static final String PHOTO="PHOTO";
    private static final String NAME="NAME";
    DialogEdit dialogUser;
    DialogEdit dialogGroup;
    DialogEdit dialogCreateGroup;
    DialogGroupJoin dialogGroupJoin;
    ExpandableListView expandableListView;
    private SharedPreferences.Editor prefEditor;
    private SharedPreferences settings;
    ImageView imageMe;
    AdapterForExpendList adapter;
    TextView textNameMe;
    Button editProfile;
    Button groupJoin;
    Button groupCreate;
    String id_user;
    ImplDB db;
    AdapterForExpendList.updateExpandableListView updateExpandableListView;
    ActivityResultLauncher<String> getContentForEdit;
    ActivityResultLauncher<String> getContentForCreate;
    long idCreateGroup;
    RetrofitService server;
    MutableLiveData<Boolean> liveDataForCreateGroup = new MutableLiveData<>();
    MutableLiveData<Boolean> liveDataForJoinGroup = new MutableLiveData<>();
    byte[] getPhoto;
    String getName;
    public GroupFragment() {
    }

//    public static GroupFragment newInstance(String name, byte[] photo) {
//        Bundle args = new Bundle();
//        args.putByteArray(PHOTO,photo);
//        args.putString(NAME, name);
//        GroupFragment fragment = new GroupFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            getPhoto = getArguments().getByteArray(PHOTO);
//            getName = getArguments().getString(NAME);
//        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        init(view);
        db.user_name().getMe().observe(getViewLifecycleOwner(), new Observer<UserNameEntity>() {
            @Override
            public void onChanged(UserNameEntity entity) {
                textNameMe.setText(entity.getName());
                imageMe.setImageBitmap(DbBitmapUtility.getImage(entity.getPhoto()));
            }
        });

        groupJoin.setOnClickListener(this::clickOnJoinGroup);
        groupCreate.setOnClickListener(this::clickOnCreateGroup);
        editProfile.setOnClickListener(this::clickOnEditProfile);
        dialogUser.ready.setOnClickListener(this::clickOnSaveDialogEdit);
        dialogGroupJoin.ready.setOnClickListener(this::clickOnButtonJoinGroup);
        dialogCreateGroup.ready.setOnClickListener(this::clickOnButtonCreateGroup);
        updateAdapter();

        return view;
    }


    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
        return fragment;
    }



    void updateAdapter(){
        db.group_name().getAllGroups().observe((LifecycleOwner) getContext(), new Observer<List<AllGroups>>() {
            @Override
            public void onChanged(List<AllGroups> allGroups) {
                adapter = new AdapterForExpendList(getContext(),allGroups,dialogGroup,getActivity(),updateExpandableListView);
                expandableListView.setAdapter(adapter);
            }
        });
    }


    void init(View view){
        expandableListView = view.findViewById(R.id.group_expand_list);
        settings = getContext().getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        prefEditor = settings.edit();
        db = new ImplDB(getContext());
        server = new RetrofitService();
        textNameMe = view.findViewById(R.id.group_head_name);
        imageMe = view.findViewById(R.id.group_head_image);
        editProfile = view.findViewById(R.id.user_edit);
        groupJoin = view.findViewById(R.id.group_join);
        groupCreate = view.findViewById(R.id.group_create);

//        USER_PATH = getContext().getFilesDir() + "/user/"  + getString(R.string.me_id);
//        GROUP_USER_PATH = getContext().getFilesDir() + "/group/" +getString(R.string.me_id);

        getContentForEdit = registerForActivityResult(new ActivityResultContracts.GetContent(), this::onActivityResultForEdit);
        getContentForCreate = registerForActivityResult(new ActivityResultContracts.GetContent(), this::onActivityResultForCreate);
        dialogUser = new DialogEdit(getContext(), getContentForEdit);
        dialogGroup = new DialogEdit(getContext(), getContentForEdit);
        dialogCreateGroup = new DialogEdit(getContext(), getContentForCreate);
        dialogGroupJoin = new DialogGroupJoin(getContext());

        updateExpandableListView = new AdapterForExpendList.updateExpandableListView() {
            @Override
            public void update(String name, Drawable image) {
                adapter = (AdapterForExpendList) expandableListView.getExpandableListAdapter();
                adapter.groupName.setText(name);
                adapter.groupImage.setImageDrawable(image);
                expandableListView.setAdapter(adapter);
            }
        };
    }
    private void onActivityResultForEdit(Uri result) {
        dialogUser.image.setImageURI(result);
        dialogGroup.image.setImageURI(result);
    }

    private void onActivityResultForCreate(Uri result) {
        dialogCreateGroup.image.setImageURI(result);
    }
    private void clickOnJoinGroup(View v){
        dialogGroupJoin.dialog.show();
    }

    private void clickOnCreateGroup(View v){
        dialogCreateGroup.name.setHint(R.string.enter_your_group);
        dialogCreateGroup.ready.setText(R.string.create_group);
        dialogCreateGroup.dialog.show();
    }

    private void clickOnEditProfile(View v){
        db.user_name().getMe().observe(this, new Observer<UserNameEntity>() {
            @Override
            public void onChanged(UserNameEntity entity) {
                dialogUser.name.setText(entity.getName());
                dialogUser.image.setImageBitmap(DbBitmapUtility.getImage(entity.getPhoto()));

            }
        });
        dialogUser.dialog.show();
    }

    private void clickOnSaveDialogEdit(View v){
        if(!dialogUser.name.getText().toString().equals("")){

            byte[] picture;
            if (dialogUser.image.getDrawable()==null){
                dialogUser.image.setImageDrawable(getContext().getDrawable(R.drawable.defaul_avatar));
                picture = DbBitmapUtility.getBytes(((BitmapDrawable) getContext().getDrawable(R.drawable.defaul_avatar)).getBitmap());
            }
            else {
                picture = DbBitmapUtility.getBytes(((BitmapDrawable) dialogUser.image.getDrawable().getCurrent()).getBitmap());
            }
            db.user_name().updateMe(dialogUser.name.getText().toString(),picture);
            db.group_name().updateMe(dialogUser.name.getText().toString(),picture);

            if (settings.getString(getString(R.string.key_for_select_group_id),"XD").equals(getString(R.string.me_id))){
                TextView name = getActivity().findViewById(R.id.main_name_group);
                ImageView image = getActivity().findViewById(R.id.main_image_group);
                name.setText(dialogUser.name.getText());
                image.setImageDrawable(dialogUser.image.getDrawable());
            }
            dialogUser.dialog.dismiss();
        }
    }


    private void clickOnButtonJoinGroup(View  v){
        if(!dialogGroupJoin.name.getText().toString().equals("")){
            String idGroup = dialogGroupJoin.name.getText().toString().toLowerCase().split("#")[0];
            for(char s:idGroup.toCharArray())
                if(!Character.isDigit(s)){
                    Toast toast = Toast.makeText(getContext(),"Некоректный id",Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

            long id_group = Long.parseLong(idGroup);
            String name = dialogGroupJoin.name.getText().toString().split("#")[1];

            boolean flag = true;
//            for(AllGroups allGroups:adapter.groups)
//                if(allGroups.groupName.getId() ==id_group)
//                    flag=false;

            if(flag){
                id_user = settings.getString(getString(R.string.key_for_me_id_server),"no_id");
                if(id_user.equals("no_id")) {
                    getMeIdForServer(false);
                    liveDataForJoinGroup.observe(this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean)
                                addUserInGroup(id_group, name);
                        }
                    });
                }
                else {
                    addUserInGroup(id_group, name);
                }
            }
            else{
                Toast toast = Toast.makeText(getContext(),"Группа уже добавлена",Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }

    void getMeIdForServer(Boolean flag){
//        String name_user =settings.getString(getString(R.string.key_for_me_name),"no_id");
//
//        String user_path = getContext().getFilesDir() + "/user/" + getString(R.string.me_id);
//        byte[] photo = ModelConverter.getPhoto(getContext(),user_path);
        db.user_name().getMe().observe(this, new Observer<UserNameEntity>() {
            @Override
            public void onChanged(UserNameEntity entity) {
                UserApi userApi = server.getRetrofit().create(UserApi.class);
                userApi.addUser(new User(entity.getName(),entity.getPhoto())).enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
//                        prefEditor.putString(getString(R.string.key_for_me_id_server), response.body().toString()).apply();
//                        String userName = settings.getString(getString(R.string.key_for_me_name), "no_name");
                        db.user_name().createUser(new UserNameEntity(response.body(),entity.getName(),entity.getPhoto()));
                        if(flag)
                            liveDataForCreateGroup.postValue(true);
                        else{
                            liveDataForJoinGroup.postValue(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        Toast toast = Toast.makeText(getContext(),"Нет доступа к серверу",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        });

    }

    private void addUserInGroup(long idGroup,String nameGroup) {
        GroupIdApi groupIdApi = server.getRetrofit().create(GroupIdApi.class);
        UserWithGroup userWithGroup = new UserWithGroup(idGroup,nameGroup,new TheGroupId(Long.parseLong(id_user),0));
        groupIdApi.saveUser(userWithGroup).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.body()){
                        getGroup(idGroup);
                    }
                    else{
                        Toast toast = Toast.makeText(getContext(),"группы не существует",Toast.LENGTH_SHORT);
                        toast.show();
                    }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),"Нет доступа к серверу",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    void getGroup(long idGroup){
        TheAllGroupApi theAllGroupApi = server.getRetrofit().create(TheAllGroupApi.class);
        theAllGroupApi.getGroupById(idGroup).enqueue(new Callback<TheAllGroup>() {
            @Override
            public void onResponse(Call<TheAllGroup> call, Response<TheAllGroup> response) {
                GroupNameEntity entity = ModelConverter.FromServerGroupToEntity(response.body(), String.valueOf(getContext().getFilesDir()));
                db.group_name().createGroup(entity);
            }

            @Override
            public void onFailure(Call<TheAllGroup> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),"Нет доступа к серверу",Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        GroupIdApi groupIdApi = server.getRetrofit().create(GroupIdApi.class);
        groupIdApi.getAllUsers(idGroup).enqueue(new Callback<List<UsersGroup>>() {
            @Override
            public void onResponse(Call<List<UsersGroup>> call, Response<List<UsersGroup>> response) {
                List<UsersGroup> usersGroups = response.body();
                List<UserNameEntity> users = ModelConverter.FromServerUserToEntity(usersGroups, String.valueOf(getContext().getFilesDir()));
                List<Integer> statuses = usersGroups.stream().map(UsersGroup::getStatus).collect(Collectors.toList());
                db.user_name().createUsers(users);
                db.group().createGroupFromList(idGroup,users,statuses);
            }

            @Override
            public void onFailure(Call<List<UsersGroup>> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),"Нет доступа к серверу",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        dialogGroupJoin.name.setText("");
        dialogGroupJoin.dialog.dismiss();
    }
    private void createGroup(String name,byte[] photo){
        TheAllGroupApi theAllGroupApi =server.getRetrofit().create(TheAllGroupApi.class);
        TheAllGroup group = new TheAllGroup();
        group.setName(name);
        group.setPhoto(photo);
        theAllGroupApi.save(group).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                idCreateGroup = response.body();
                db.group_name().createGroup(new GroupNameEntity(idCreateGroup,name,photo));
                saveUser(name);
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),"Нет доступа к серверу",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        dialogCreateGroup.image.setImageDrawable(null);
        dialogCreateGroup.name.setText("");
        dialogCreateGroup.dialog.dismiss();
    }
    private void saveUser(String name){
        GroupIdApi groupIdApi = server.getRetrofit().create(GroupIdApi.class);
        UserWithGroup user = new UserWithGroup();
        user.setId_group(idCreateGroup);
        user.setName_group(name);
        Long myId = Long.parseLong(settings.getString(getString(R.string.key_for_me_id_server),"no_id"));
        user.setUser(new TheGroupId(myId,1));

        groupIdApi.saveUser(user).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.body()){
                    Toast toast = Toast.makeText(getContext(),"ошибка создания группы",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    db.group().createGroup(new GroupEntity(myId, idCreateGroup, true));
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(),"Нет доступа к серверу",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void clickOnButtonCreateGroup(View v){
        if(!dialogCreateGroup.name.getText().toString().equals("")){
            byte[] photo;
            if(dialogCreateGroup.image.getDrawable()==null){
                photo = DbBitmapUtility.getBytes(((BitmapDrawable) getContext().getDrawable(R.drawable.defaul_avatar)).getBitmap());
            }
            else{
                photo =  DbBitmapUtility.getBytes(((BitmapDrawable) dialogCreateGroup.image.getDrawable().getCurrent()).getBitmap());
            }

            String myIdForServer = settings.getString(getString(R.string.key_for_me_id_server),"no_id");
            if(myIdForServer.equals("no_id")){
                getMeIdForServer(true);
                liveDataForCreateGroup.observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean)
                            createGroup(dialogCreateGroup.name.getText().toString(),photo);
                    }
                });
            }
            else {
                createGroup(dialogCreateGroup.name.getText().toString(), photo);
            }
        }
    }
}