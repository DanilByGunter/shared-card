package com.project.shared_card.database.entity.links;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.database.entity.Group;
import com.project.shared_card.database.entity.Users;

import java.util.List;

public class GroupWithUsers {
    @Embedded
    public Group group;
    @Relation(parentColumn = "id", entityColumn = "groupId")
    public List<Users> listUser;
}
