package com.project.shared_card.database.entity.links;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.database.entity.Check;
import com.project.shared_card.database.entity.Group;

import java.util.List;

public class GroupWithChecks {
    @Embedded
    public Group group;
    @Relation(parentColumn = "id", entityColumn = "groupId")
    public List<Check> listCheck;
}
