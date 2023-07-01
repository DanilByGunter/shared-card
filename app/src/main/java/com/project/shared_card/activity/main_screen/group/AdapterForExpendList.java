package com.project.shared_card.activity.main_screen.group;

import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.activity.main_screen.check.callback.ButtonClickCallback;
import com.project.shared_card.activity.main_screen.group.dialog.DialogEditGroup;
import com.project.shared_card.activity.main_screen.group.dialog.DialogEditProfile;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group.GroupWithUser;
import com.project.shared_card.database.entity.group_name.AllGroups;
import com.project.shared_card.databinding.GroupHeadBinding;
import com.project.shared_card.databinding.GroupUserBinding;

import java.util.List;

public class AdapterForExpendList  extends BaseExpandableListAdapter {

    DialogEditProfile dialog;
    private List<AllGroups> groups;
    FragmentActivity activity;
    GroupHeadBinding headBinding;
    GroupUserBinding userBinding;

    ButtonClickCallback clickEdit = () -> {

    };

    public AdapterForExpendList() {

    }

    public void update(List<AllGroups> groups){
        this.groups =groups;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return groups == null ? 0 : groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).groupEntities.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).groupEntities.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            headBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.group_head,parent, false);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
        }
        headBinding.setAllGroups(groups.get(groupPosition));
        headBinding.groupHeadImage.setImageBitmap(DbBitmapUtility.getImage(groups.get(groupPosition).groupName.getPhoto()));
        headBinding.groupEdit.setOnClickListener((v) -> {
            DialogEditGroup dialog = DialogEditGroup.getInstance(groups.get(groupPosition).groupName);
            dialog.show(activity.getSupportFragmentManager(), "dialog");
        });

        headBinding.executePendingBindings();
        return headBinding.getRoot();
    }



    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            userBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.group_user,parent,false);
        }
        GroupWithUser user = groups.get(groupPosition).groupEntities.get(childPosition);

        userBinding.groupUserName.setText(user.userName.getName());
        userBinding.groupUserImage.setImageBitmap(DbBitmapUtility.getImage(user.userName.getPhoto()));

        return userBinding.getRoot();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public List<AllGroups> getGroups() {
        return groups;
    }
}
