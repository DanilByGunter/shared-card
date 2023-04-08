package com.project.shared_card.database.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class GroupWithUsers {
    @Embedded
    public Group group;
    @Relation(parentColumn = "id", entityColumn = "groupId")
    public List<Users> listUser;
}
