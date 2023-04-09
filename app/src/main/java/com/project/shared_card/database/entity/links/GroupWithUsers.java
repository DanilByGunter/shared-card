package com.project.shared_card.database.entity.links;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.database.entity.GroupEntity;
import com.project.shared_card.database.entity.UsersEntity;

import java.util.List;

public class GroupWithUsers {
    @Embedded
    public GroupEntity groupEntity;
    @Relation(parentColumn = "id", entityColumn = "groupId")
    public List<UsersEntity> listUser;
}
