package com.project.shared_card.database.entity.links;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.database.entity.group.GroupEntity;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;

import java.util.List;

public class GroupsWithGroup {
    @Embedded
    public GroupNameEntity groupNameEntity;
    @Relation(parentColumn = "id", entityColumn = "")
    public List<GroupEntity> listGroupEntity;
}
