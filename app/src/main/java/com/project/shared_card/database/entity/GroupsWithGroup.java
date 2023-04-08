package com.project.shared_card.database.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class GroupsWithGroup {
    @Embedded
    public Groups groups;
    @Relation(parentColumn = "id", entityColumn = "")
    public List<Group> listGroup;
}
