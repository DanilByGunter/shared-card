package com.project.shared_card.activity.main_screen.group;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.shared_card.R;
import com.project.shared_card.database.entity.group_name.AllGroups;

import java.util.List;

public class AdapterForExpendList  extends BaseExpandableListAdapter {
    private Context context;
    private SharedPreferences settings;
    Dialog dialogEditGroup;
    private List<AllGroups> groups;
    TextView userName;
    TextView groupName;
    TextView groupId;
    ImageView groupImage;
    ImageView userImage;
    Button groupEdit;

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
        settings = context.getSharedPreferences(context.getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        dialogEditGroup = new Dialog(context);
        dialogEditGroup.setContentView(R.layout.dialog_edit_profile);
        dialogEditGroup.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        EditText dialogNameUser = dialogEditGroup.findViewById(R.id.dialog_edit_name);
        ImageView dialogImage = dialogEditGroup.findViewById(R.id.dialog_image);
        Button dialogReady = dialogEditGroup.findViewById(R.id.dialog_ready);

        dialogNameUser.setText(groups.get(groupPosition).groupName.getName());
        dialogNameUser.setHint(context.getString(R.string.enter_your_group));
        dialogImage.setImageURI(Uri.parse(groups.get(groupPosition).groupName.getPhoto()));


        groupName = convertView.findViewById(R.id.group_head_name);
        groupId = convertView.findViewById(R.id.group_head_id);
        groupImage = convertView.findViewById(R.id.group_head_image);
        groupEdit = convertView.findViewById(R.id.group_edit);

        groupId.setText("ID: " + groups.get(groupPosition).groupName.getId());
        groupName.setText(groups.get(groupPosition).groupName.getName());
        groupImage.setImageURI(Uri.parse(groups.get(groupPosition).groupName.getPhoto()));
        groupEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditGroup.show();
            }
        });
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
