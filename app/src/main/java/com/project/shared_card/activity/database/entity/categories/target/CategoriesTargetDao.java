package com.project.shared_card.activity.database.entity.categories.target;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoriesTargetDao {
    @Insert
    void add(List<CategoriesTargetEntity> entity);
    @Query("Select * from category_target")
    LiveData<List<CategoriesTargetEntity>> getAll();
}
