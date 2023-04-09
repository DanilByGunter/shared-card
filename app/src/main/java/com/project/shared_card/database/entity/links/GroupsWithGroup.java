package com.project.shared_card.database.entity.links;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.database.entity.Group;
import com.project.shared_card.database.entity.Groups;

import java.util.List;

public class GroupsWithGroup {
    @Embedded
    public Groups groups;
    @Relation(parentColumn = "id", entityColumn = "")
    public List<Group> listGroup;
}
