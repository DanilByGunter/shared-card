package com.project.shared_card.database.entity.check;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CheckDao {
    @Query("select * from `check` where group_name_id = :id")
    LiveData<List<FullCheck>> getAll(long id);
    @Insert
    void addCheck(CheckEntity check);
}
