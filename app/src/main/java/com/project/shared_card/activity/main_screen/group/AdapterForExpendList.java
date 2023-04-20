package com.project.shared_card.activity.main_screen.group;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.shared_card.R;
import com.project.shared_card.database.entity.group_name.AllGroups;

import java.util.List;

public class AdapterForExpendList  extends BaseExpandableListAdapter {
    private Context context;
    private List<AllGroups> groups;
    TextView userName;
    TextView groupName;
    TextView groupId;
    ImageView groupImage;
    ImageView userImage;

    public AdapterForExpendList(Context context, List<AllGroups> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.group_head, parent, false);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
        }
        groupName = convertView.findViewById(R.id.group_head_name);
        groupId = convertView.findViewById(R.id.group_head_id);
        groupImage = convertView.findViewById(R.id.group_head_image);

        groupId.setText("ID: " + groups.get(groupPosition).groupName.getId());
        groupName.setText(groups.get(groupPosition).groupName.getName());
        groupImage.setImageURI(Uri.parse(groups.get(groupPosition).groupName.getPhoto()));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.group_user, parent, false);
        }
        userName = convertView.findViewById(R.id.group_user_name);
        userImage = convertView.findViewById(R.id.group_user_image);

        userName.setText(groups.get(groupPosition).groupEntities.get(childPosition).userName.getName());
        userImage.setImageURI(Uri.parse(groups.get(groupPosition).groupEntities.get(childPosition).userName.getPhoto()));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
