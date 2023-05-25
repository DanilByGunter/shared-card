package com.project.shared_card.activity.main_screen.group;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.shared_card.R;
import com.project.shared_card.activity.converter.DbBitmapUtility;
import com.project.shared_card.activity.main_screen.group.dialog.DialogEdit;
import com.project.shared_card.database.ImplDB;
import com.project.shared_card.database.entity.group_name.AllGroups;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class AdapterForExpendList  extends BaseExpandableListAdapter {
    private Context context;
    private SharedPreferences settings;
    private SharedPreferences.Editor prefEditor;
    DialogEdit dialog;
    public List<AllGroups> groups;
    TextView userName;
    TextView groupName;
    TextView groupId;
    ImageView groupImage;
    ImageView userImage;
    Button groupEdit;
    View mainToolBar;
    String GROUP_PATH;
    String USER_PATH;
    long GROUP_ID;
    int groupPosition;
    int childPosition;
    interface updateExpandableListView{
        void update(String name, Drawable image);
    }
    updateExpandableListView expandableListView;

    public AdapterForExpendList(Context context, List<AllGroups> groups, DialogEdit dialog, View mainToolBar, updateExpandableListView updateExpandableListView) {
        this.context = context;
        this.groups = groups;
        this.mainToolBar = mainToolBar;
        this.dialog = dialog;
        this.expandableListView= updateExpandableListView;
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
        initGroup(convertView, groupPosition);
        dialog.name.setHint(context.getString(R.string.enter_your_group));

        groupId.setText(groups.get(groupPosition).groupName.getId() + "#" + groups.get(groupPosition).groupName.getName());
        groupName.setText(groups.get(groupPosition).groupName.getName());
        groupImage.setImageURI(Uri.parse(GROUP_PATH));

        groupEdit.setOnClickListener(this::clickOnBtnGroupEdit);
        dialog.ready.setOnClickListener(this::clickOnBtnReady);

        return convertView;
    }


    private void clickOnBtnGroupEdit(View v){
        dialog.name.setText(groups.get(groupPosition).groupName.getName());
        dialog.image.setImageURI(Uri.parse(GROUP_PATH));
        dialog.dialog.show();
    }

    private void clickOnBtnReady(View v){
        if(!dialog.name.getText().toString().equals("")){
            ImplDB db = new ImplDB(context);
            db.group_name().updateForId(GROUP_ID, dialog.name.getText().toString());

            byte[] picture;
            if (dialog.image.getDrawable()==null){
                dialog.image.setImageDrawable(context.getDrawable(R.drawable.defaul_avatar));
                picture = DbBitmapUtility.getBytes(((BitmapDrawable) context.getDrawable(R.drawable.defaul_avatar)).getBitmap());
            }
            else {
                picture = DbBitmapUtility.getBytes(((BitmapDrawable) dialog.image.getDrawable().getCurrent()).getBitmap());
            }
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream(GROUP_PATH);
                        fos.write(picture);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
            expandableListView.update(String.valueOf(dialog.name.getText()),dialog.image.getDrawable());
            groups.get(groupPosition).groupName.setName(dialog.name.getText().toString());

            String selectedGroupId= settings.getString(context.getString(R.string.key_for_select_group_id), "XD");

            if (selectedGroupId.equals(String.valueOf(GROUP_ID))){
                TextView name = mainToolBar.findViewById(R.id.main_name_group);
                ImageView image = mainToolBar.findViewById(R.id.main_image_group);
                name.setText(dialog.name.getText());
                image.setImageDrawable(dialog.image.getDrawable());
            }

            dialog.dialog.dismiss();
        }
    }

    void initGroup(View v, int groupPosition){
        settings = context.getSharedPreferences(context.getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        prefEditor = settings.edit();
        this.groupPosition= groupPosition;
        groupName = v.findViewById(R.id.group_head_name);
        groupId = v.findViewById(R.id.group_head_id);
        groupImage = v.findViewById(R.id.group_head_image);
        groupEdit = v.findViewById(R.id.group_edit);

        GROUP_ID = groups.get(groupPosition).groupName.getId();
        GROUP_PATH = context.getFilesDir() + "/group/" +GROUP_ID;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.group_user, parent, false);
        }
        initUser(convertView,childPosition);

        userName.setText(groups.get(groupPosition).groupEntities.get(childPosition).userName.getName());
        userImage.setImageURI(Uri.parse(USER_PATH));

        return convertView;
    }

    void initUser(View v, int childPosition){
        userName = v.findViewById(R.id.group_user_name);
        userImage = v.findViewById(R.id.group_user_image);

        USER_PATH = context.getFilesDir() + "/user/" + groups.get(groupPosition).groupEntities.get(childPosition).userName.getId();

        this.childPosition = childPosition;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
