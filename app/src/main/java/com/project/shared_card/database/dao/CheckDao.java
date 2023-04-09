package com.project.shared_card.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.shared_card.database.entity.CheckEntity;

import java.util.List;

@Dao
public interface CheckDao {
    @Insert
    void createCheck(CheckEntity check);
    @Query("select * from `check`")
    List<CheckEntity> findAll();
    @Query("select * from `check` where id = :id")
    CheckEntity findById(long id);
}
