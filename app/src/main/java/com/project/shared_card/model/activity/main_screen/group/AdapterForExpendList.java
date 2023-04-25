package com.project.shared_card.model.activity.main_screen.group;

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
import com.project.shared_card.model.activity.converter.DbBitmapUtility;
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
    private List<AllGroups> groups;
    TextView userName;
    TextView groupName;
    TextView groupId;
    ImageView groupImage;
    ImageView userImage;
    Button groupEdit;
    //ActivityResultLauncher<String> getContent;
    View mainToolBar;
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
        settings = context.getSharedPreferences(context.getString(R.string.key_for_shared_preference), Context.MODE_PRIVATE);
        prefEditor = settings.edit();
        long GROUP_ID = groups.get(groupPosition).groupName.getId();
        String GROUP_PATH = context.getFilesDir() + "/group/" +GROUP_ID +".png";
        dialog.name.setHint(context.getString(R.string.enter_your_group));

        groupName = convertView.findViewById(R.id.group_head_name);
        groupId = convertView.findViewById(R.id.group_head_id);
        groupImage = convertView.findViewById(R.id.group_head_image);
        groupEdit = convertView.findViewById(R.id.group_edit);
        dialog.ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dialog.name.getText().toString().equals("") && dialog.image.getDrawable().getCurrent()!=null){
                    //TODO server
                    ImplDB db = new ImplDB(context);
                    db.getGroupNameRepository().updateForId(GROUP_ID, dialog.name.getText().toString());
                    byte[] picture = DbBitmapUtility.getBytes(((BitmapDrawable) dialog.image.getDrawable().getCurrent()).getBitmap());
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
                    String select_group_id = settings.getString(context.getString(R.string.key_for_select_group_id), "XD");
                    String select_id= select_group_id.split("#")[0];
                    groups.get(groupPosition).groupName.setName(dialog.name.getText().toString());
                    if (select_id.equals(String.valueOf(GROUP_ID))){
                        String id = GROUP_ID+"#" + dialog.name.getText();
                        prefEditor.putString(context.getString(R.string.key_for_select_group_id), id).apply();
                        TextView name = mainToolBar.findViewById(R.id.main_name_group);
                        ImageView image = mainToolBar.findViewById(R.id.main_image_group);
                        name.setText(dialog.name.getText());
                        image.setImageDrawable(dialog.image.getDrawable());
                    }
                    dialog.dialog.dismiss();
                }
            }
        });

        groupId.setText("ID: " + groups.get(groupPosition).groupName.getId());
        groupName.setText(groups.get(groupPosition).groupName.getName());
        String path = context.getFilesDir() + "/group/" + groups.get(groupPosition).groupName.getId() + ".png";
        groupImage.setImageURI(Uri.parse(path));
        groupEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.name.setText(groups.get(groupPosition).groupName.getName());
                dialog.image.setImageURI(Uri.parse(path));
                dialog.dialog.show();
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
        String path = context.getFilesDir() + "/user/" + groups.get(groupPosition).groupEntities.get(childPosition).userName.getId() + ".png";
        userImage.setImageURI(Uri.parse(path));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
