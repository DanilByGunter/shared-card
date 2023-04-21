package com.project.shared_card.activity.main_screen.group;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.shared_card.R;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group_name.AllGroups;

import java.util.List;

public class GroupFragment extends Fragment {
    Dialog dialogEditUser;
    private SharedPreferences settings;

    public GroupFragment() {
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
        String USER_PATH = getContext().getFilesDir() + "/user/" + "me.png";

        TextView textName = view.findViewById(R.id.group_head_name);
        ImageView imageView = view.findViewById(R.id.group_head_image);
        Button editProfile = view.findViewById(R.id.user_edit);
        settings = getContext().getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);

        dialogEditUser = new Dialog(getContext());
        dialogEditUser.setContentView(R.layout.dialog_edit_profile);
        dialogEditUser.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        EditText dialogNameUser = dialogEditUser.findViewById(R.id.dialog_edit_name);
        ImageView dialogImage = dialogEditUser.findViewById(R.id.dialog_image);
        Button dialogReady = dialogEditUser.findViewById(R.id.dialog_ready);
        dialogNameUser.setText(settings.getString(getString(R.string.key_for_user_name),"XD"));
        dialogImage.setImageURI(Uri.parse(USER_PATH));

        textName.setText(settings.getString(getString(R.string.key_for_user_name),"XD"));


        imageView.setImageURI(Uri.parse(USER_PATH));

        ImplDB db = new ImplDB(getContext());
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditUser.show();
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