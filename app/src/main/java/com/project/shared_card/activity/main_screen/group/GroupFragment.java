package com.project.shared_card.activity.main_screen.group;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.activity.main_screen.group.dialog.DialogEditProfile;
import com.project.shared_card.activity.main_screen.group.dialog.DialogGroupCreate;
import com.project.shared_card.activity.main_screen.group.dialog.DialogGroupJoin;
import com.project.shared_card.databinding.FragmentGroupBinding;

public class GroupFragment extends Fragment {
    AdapterForExpendList adapter;
    FragmentGroupBinding binding;
    GroupFragmentViewModel viewModel;
    public GroupFragment() {
    }

    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_group, container,false);

        binding.myProfile.groupJoin.setOnClickListener(this::clickOnJoinGroup);
        binding.myProfile.groupCreate.setOnClickListener(this::clickOnCreateGroup);
        binding.myProfile.userEdit.setOnClickListener(this::clickOnEditProfile);

        adapter = new AdapterForExpendList();
        binding.groupExpandList.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(GroupFragmentViewModel.class);

        getAllGroup();
        getYourself();
    }
    void getYourself(){
      viewModel.getGroup().observe(getViewLifecycleOwner(), groupName -> {
          binding.myProfile.groupHeadName.setText(groupName.getName());
          binding.myProfile.groupHeadImage.setImageBitmap(DbBitmapUtility.getImage(groupName.getPhoto()));
      });
    }

    void getAllGroup(){
        viewModel.getAllGroups().observe(getViewLifecycleOwner(), allGroups -> {
            adapter.update(allGroups);
        });
    }

    private void clickOnJoinGroup(View v){
        DialogGroupJoin dialogJoin = DialogGroupJoin.getInstance(adapter.getGroups());
        dialogJoin.show(getChildFragmentManager(),"dialog");
    }

    private void clickOnCreateGroup(View v){
        DialogGroupCreate dialogCreate = new DialogGroupCreate();
        dialogCreate.show(getChildFragmentManager(),"dialog");
    }

    private void clickOnEditProfile(View v){
        DialogEditProfile dialogEdit = new DialogEditProfile();
        dialogEdit.show(getChildFragmentManager(),"dialog");
    }
}