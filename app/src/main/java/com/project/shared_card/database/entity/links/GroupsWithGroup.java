package com.project.shared_card.database.entity.links;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.database.entity.GroupEntity;
import com.project.shared_card.database.entity.GroupsEntity;

import java.util.List;

public class GroupsWithGroup {
    @Embedded
    public GroupsEntity groupsEntity;
    @Relation(parentColumn = "id", entityColumn = "")
    public List<GroupEntity> listGroupEntity;
}
