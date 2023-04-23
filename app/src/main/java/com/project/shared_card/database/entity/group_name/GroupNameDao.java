package com.project.shared_card.database.entity.group_name;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.project.shared_card.database.entity.user_name.UserNameEntity;

import java.util.List;

@Dao
public interface GroupNameDao {
    @Insert
    void createGroup(GroupNameEntity group);
    @Query("SELECT * FROM group_name")
    LiveData<List<AllGroups>> getALLGroup();

    @Update
    void update(GroupNameEntity entity);
}
