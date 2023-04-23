package com.project.shared_card.activity.main_screen.group;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
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
import android.widget.Toolbar;

import com.project.shared_card.R;
import com.project.shared_card.activity.main_screen.MainActivity;
import com.project.shared_card.converter.DbBitmapUtility;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group_name.AllGroups;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GroupFragment extends Fragment {
    DialogEditUser dialog;
    private SharedPreferences.Editor prefEditor;
    private SharedPreferences settings;
    View mainToolBar;

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
        ExpandableListView expandableListView = view.findViewById(R.id.group_expand_list);
        settings = getContext().getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        String USER_ID = settings.getString(getString(R.string.key_for_user_id),"XD");
        String USER_PATH = getContext().getFilesDir() + "/user/"  + USER_ID +".png";
        String GROUP_USER_PATH = getContext().getFilesDir() + "/group/" + "-1.png";
        ImplDB db = new ImplDB(getContext());

        TextView textName = view.findViewById(R.id.group_head_name);
        ImageView imageView = view.findViewById(R.id.group_head_image);
        Button editProfile = view.findViewById(R.id.user_edit);
        ActivityResultLauncher<String> getContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                dialog.dialogImage.setImageURI(result);
            }
        });
        dialog = new DialogEditUser(getContext(),getContent);
        dialog.dialogReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog.dialogImage.getDrawable()!=null && !dialog.dialogNameUser.getText().equals(null)){
                    //TODO server
                    prefEditor = settings.edit();
                    prefEditor.putString(getString(R.string.key_for_user_name), dialog.dialogNameUser.getText().toString()).apply();
                    db.getUserNameRepository().updateMe(dialog.dialogNameUser.getText().toString());
                    db.getGroupNameRepository().updateMe(dialog.dialogNameUser.getText().toString());
                    byte[] picture = DbBitmapUtility.getBytes(((BitmapDrawable) dialog.dialogImage.getDrawable().getCurrent()).getBitmap());
                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream(USER_PATH);
                        fos.write(picture);
                        fos = new FileOutputStream(GROUP_USER_PATH);
                        fos.write(picture);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    imageView.setImageDrawable(dialog.dialogImage.getDrawable());
                    if (settings.getString(getString(R.string.key_for_select_group_id),"XD").split("#")[0].equals("-1")){
                        String id = "-1#" + dialog.dialogNameUser.getText();
                        prefEditor.putString(getString(R.string.key_for_select_group_id), id).apply();
                        TextView name = mainToolBar.findViewById(R.id.main_name_group);
                        ImageView image = mainToolBar.findViewById(R.id.main_image_group);
                        name.setText(dialog.dialogNameUser.getText());
                        image.setImageDrawable(dialog.dialogImage.getDrawable());
                    }
                }
            }
        });


        textName.setText(settings.getString(getString(R.string.key_for_user_name),"XD"));
        imageView.setImageURI(Uri.parse(USER_PATH));


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dialogNameUser.setText(settings.getString(getString(R.string.key_for_user_name),"XD"));
                dialog.dialogImage.setImageURI(Uri.parse(USER_PATH));
                dialog.dialog.show();
            }
        });
        db.getGroupNameRepository().getAllGroups().observe((LifecycleOwner) getContext(), new Observer<List<AllGroups>>() {
            @Override
            public void onChanged(List<AllGroups> allGroups) {
                AdapterForExpendList adapter = new AdapterForExpendList(getContext(),allGroups);
                expandableListView.setAdapter(adapter);
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group, container, false);
    }
}