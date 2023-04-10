package com.project.shared_card.database.entity.group_name;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface GroupNameDao {
    @Insert
    void createGroup(GroupNameEntity group);
}
