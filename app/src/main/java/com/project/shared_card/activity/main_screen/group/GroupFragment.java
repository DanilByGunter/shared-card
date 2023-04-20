package com.project.shared_card.activity.main_screen.group;

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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.shared_card.R;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group_name.AllGroups;

import java.util.List;

public class GroupFragment extends Fragment {

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
        TextView textId = view.findViewById(R.id.group_head_id);
        TextView textName = view.findViewById(R.id.group_head_name);
        ImageView imageView = view.findViewById(R.id.group_head_image);
        settings = getContext().getSharedPreferences(getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);

        textId.setText("ID: " +settings.getString(getString(R.string.key_for_user_id),"-1"));
        textName.setText(settings.getString(getString(R.string.key_for_user_name),"XD"));

        String userPath = getContext().getFilesDir() + "/user/" + "me.png";
        imageView.setImageURI(Uri.parse(userPath));

        ImplDB db = new ImplDB(getContext());
//        db.getGroupRepository().getAllGroup().observe((LifecycleOwner) getContext(), new Observer<List<GroupEntity>>() {
//            @Override
//            public void onChanged(List<GroupEntity> groupDaos) {
//                System.out.println(groupDaos);
//            }
//        });
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