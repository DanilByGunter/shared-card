package com.project.shared_card.database.entity.group;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.activity.database.entity.user_name.UserNameEntity;

public class GroupWithUser {
    @Embedded
    public GroupEntity groupEntity;
    @Relation(
            parentColumn = "user_name_id",
            entityColumn = "id"
    )
    public UserNameEntity userName;
}
