package com.project.shared_card.activity.database.entity.group_name;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.activity.database.entity.group.GroupEntity;
import com.project.shared_card.activity.database.entity.group.GroupWithUser;

import java.util.List;

public class AllGroups {
        @Embedded
        public GroupNameEntity groupName;
        @Relation(parentColumn = "id",entityColumn = "group_name_id",entity = GroupEntity.class)
        public List<GroupWithUser> groupEntities;

}
