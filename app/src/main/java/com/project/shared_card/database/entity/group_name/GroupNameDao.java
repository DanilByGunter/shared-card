package com.project.shared_card.database.entity.group_name;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GroupNameDao {
    @Insert
    void createGroup(GroupNameEntity group);
    @Insert
    void createGroups(List<GroupNameEntity> groups);
    @Query("SELECT * FROM group_name where id>0")
    LiveData<List<AllGroups>> getALLGroup();

    @Query("select * from group_name where id=:id")
    LiveData<GroupNameEntity> getGroupById(Long id);
    @Update
    void update(GroupNameEntity entity);
}
