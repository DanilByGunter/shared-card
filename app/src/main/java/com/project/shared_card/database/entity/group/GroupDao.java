package com.project.shared_card.database.entity.group;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface GroupDao {
    @Insert
    void createGroup(GroupEntity entity);
}
