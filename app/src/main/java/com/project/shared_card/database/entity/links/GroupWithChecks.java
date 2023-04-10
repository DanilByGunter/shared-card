package com.project.shared_card.database.entity.links;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.database.entity.check.CheckEntity;
import com.project.shared_card.database.entity.group.GroupEntity;

import java.util.List;

public class GroupWithChecks {
    @Embedded
    public GroupEntity groupEntity;
    @Relation(parentColumn = "id", entityColumn = "groupId")
    public List<CheckEntity> listCheck;
}
