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
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.activity.main_screen.group.dialog.DialogEdit;
import com.project.shared_card.activity.main_screen.group.dialog.DialogGroupJoin;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group.GroupEntity;
import com.project.shared_card.database.entity.group_name.AllGroups;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;
import com.project.shared_card.database.entity.user_name.UserNameDao;
import com.project.shared_card.database.entity.user_name.UserNameEntity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GroupFragment extends Fragment {
    DialogEdit dialogUser;
    DialogEdit dialogGroup;
    DialogEdit dialogCreateGroup;
    DialogGroupJoin dialogGroupJoin;
    ExpandableListView expandableListView;
    private SharedPreferences.Editor prefEditor;
    private SharedPreferences settings;
    View mainToolBar;
    ImageView imageMe;
    AdapterForExpendList adapter;
    TextView textNameMe;
    Button editProfile;
    Button groupJoin;
    Button groupCreate;
    String USER_PATH;
    String GROUP_USER_PATH;
    String GROUP_CREATE_PATH;
    ImplDB db;
    AdapterForExpendList.updateExpandableListView updateExpandableListView;
    ActivityResultLauncher<String> getContentForEdit;
    ActivityResultLauncher<String> getContentForCreate;
    public GroupFragment() {
    }

    public GroupFragment(View viewById) {
        mainToolBar = viewById;
    }

    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        textNameMe.setText(settings.getString(getString(R.string.key_for_me_name),"no_name"));
        imageMe.setImageURI(Uri.parse(USER_PATH));

        groupJoin.setOnClickListener(this::clickOnJoinGroup);
        groupCreate.setOnClickListener(this::clickOnCreateGroup);
        editProfile.setOnClickListener(this::clickOnEditProfile);
        dialogUser.ready.setOnClickListener(this::clickOnSaveDialogEdit);
        dialogGroupJoin.ready.setOnClickListener(this::clickOnButtonJoinGroup);
        dialogCreateGroup.ready.setOnClickListener(this::clickOnButtonCreateGroup);
        updateAdapter();

    }
    void updateAdapter(){
        db.group_name().getAllGroups().observe((LifecycleOwner) getContext(), new Observer<List<AllGroups>>() {
            @Override
            public void onChanged(List<AllGroups> allGroups) {
                adapter = new AdapterForExpendList(getContext(),allGroups,dialogGroup,mainToolBar,updateExpandableListView);
                expandableListView.setAdapter(adapter);
            }
        });
    }


    void init(View view){
        expandableListView = view.findViewById(R.id.group_expand_list);
        settings = getContext().getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        prefEditor = settings.edit();
        db = new ImplDB(getContext());

        textNameMe = view.findViewById(R.id.group_head_name);
        imageMe = view.findViewById(R.id.group_head_image);
        editProfile = view.findViewById(R.id.user_edit);
        groupJoin = view.findViewById(R.id.group_join);
        groupCreate = view.findViewById(R.id.group_create);

        USER_PATH = getContext().getFilesDir() + "/user/"  + getString(R.string.me_id);
        GROUP_USER_PATH = getContext().getFilesDir() + "/group/" +getString(R.string.me_id);

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
        dialogCreateGroup.dialog.show();
    }


    private void clickOnEditProfile(View v){
        dialogUser.name.setText(settings.getString(getString(R.string.key_for_me_name),"XD"));
        dialogUser.image.setImageURI(Uri.parse(USER_PATH));
        dialogUser.dialog.show();
    }
    private void clickOnSaveDialogEdit(View v){
        if(!dialogUser.name.getText().toString().equals("")){
            prefEditor.putString(getString(R.string.key_for_me_name), dialogUser.name.getText().toString()).apply();

            db.user_name().updateMe(dialogUser.name.getText().toString());
            db.group_name().updateMe(dialogUser.name.getText().toString());

            byte[] picture;
            if (dialogUser.image.getDrawable()==null){
                dialogUser.image.setImageDrawable(getContext().getDrawable(R.drawable.defaul_avatar));
                picture = DbBitmapUtility.getBytes(((BitmapDrawable) getContext().getDrawable(R.drawable.defaul_avatar)).getBitmap());
            }
            else {
                picture = DbBitmapUtility.getBytes(((BitmapDrawable) dialogUser.image.getDrawable().getCurrent()).getBitmap());
            }
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream(USER_PATH);
                        fos.write(picture);
                        fos = new FileOutputStream(GROUP_USER_PATH);
                        fos.write(picture);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
            imageMe.setImageDrawable(dialogUser.image.getDrawable());
            textNameMe.setText(dialogUser.name.getText());

            if (settings.getString(getString(R.string.key_for_select_group_id),"XD").equals(getString(R.string.me_id))){
                TextView name = mainToolBar.findViewById(R.id.main_name_group);
                ImageView image = mainToolBar.findViewById(R.id.main_image_group);
                name.setText(dialogUser.name.getText());
                image.setImageDrawable(dialogUser.image.getDrawable());
            }
            dialogUser.dialog.dismiss();
        }
    }

    private void clickOnButtonJoinGroup(View  v){
        if(!dialogGroupJoin.name.getText().toString().equals("")){
            //todo СЕРВЕЕЕЕЕЕЕЕЕЕЕР
            long id = Long.parseLong(dialogGroupJoin.name.getText().toString().split("#")[0]);
            String name = dialogGroupJoin.name.getText().toString().split("#")[1];
            List<UserNameEntity> users = null;
            GroupNameEntity groupName = null;
            GroupEntity group =null;
            db.user_name().createUsers(users);
            db.group_name().createGroup(groupName);
            db.group().createGroup(group);
            updateAdapter();

            dialogGroupJoin.name.setText("");
            dialogGroupJoin.dialog.dismiss();
        }
    }

    private void clickOnButtonCreateGroup(View v){
        if(!dialogCreateGroup.name.getText().toString().equals("")){

            String myIdForServer = settings.getString(getString(R.string.key_for_me_id_server),"no_id");
            if(myIdForServer.equals("no_id")){

            }
            //todo СЕРВЕЕЕЕЕЕЕЕЕЕЕР
            long idGroup = 0;

            db.group_name().createGroup(new GroupNameEntity(idGroup,dialogCreateGroup.name.getText().toString()));
            db.group().createGroup(new GroupEntity(Long.parseLong(getString(R.string.me_id)), idGroup, true));

            byte[] picture = DbBitmapUtility.getBytes(((BitmapDrawable) dialogUser.image.getDrawable().getCurrent()).getBitmap());
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    FileOutputStream fos;
                    try {
                        GROUP_CREATE_PATH = getContext().getFilesDir() + "/group/" + idGroup;
                        fos = new FileOutputStream(GROUP_CREATE_PATH);
                        fos.write(picture);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
            updateAdapter();
            dialogCreateGroup.image.setImageDrawable(null);
            dialogCreateGroup.name.setText("");
            dialogCreateGroup.dialog.dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group, container, false);
    }
}