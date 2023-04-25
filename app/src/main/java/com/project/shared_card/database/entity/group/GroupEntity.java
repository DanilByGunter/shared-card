package com.project.shared_card.database.entity.group;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.project.shared_card.database.entity.group_name.GroupNameEntity;
import com.project.shared_card.database.entity.user_name.UserNameEntity;
//       foreignKeys = {
//        @ForeignKey(entity = UserNameEntity.class,parentColumns = "id",childColumns ="user_name_id"),
//        @ForeignKey(entity = GroupNameEntity.class,parentColumns = "id",childColumns ="group_name_id")}
@Entity(tableName = "group",
        primaryKeys = {"user_name_id","group_name_id"})
public class GroupEntity {

    @ColumnInfo(name = "user_name_id")
    private long userNameId;
    @ColumnInfo(name = "group_name_id")
    private long groupNameId;
    private Boolean status;

    public GroupEntity(long userNameId, long groupNameId, Boolean status) {
        this.userNameId = userNameId;
        this.groupNameId = groupNameId;
        this.status = status;
    }

    public long getUserNameId() {
        return userNameId;
    }

    public void setUserNameId(long userNameId) {
        this.userNameId = userNameId;
    }

    public long getGroupNameId() {
        return groupNameId;
    }

    public void setGroupNameId(long groupNameId) {
        this.groupNameId = groupNameId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
