package com.project.shared_card.database.entity.groups;

import androidx.room.Dao;
import androidx.room.Insert;

import com.project.shared_card.database.entity.GroupEntity;

@Dao
public interface GroupsDao {
    @Insert
    void createGroup(GroupsEntity group);
}
