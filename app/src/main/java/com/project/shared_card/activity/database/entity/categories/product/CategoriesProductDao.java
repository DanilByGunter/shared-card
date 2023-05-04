package com.project.shared_card.activity.database.entity.categories.product;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoriesProductDao {
    @Insert
    void add(List<CategoriesProductEntity> entity);
    @Query("Select * from category_product")
    LiveData<List<CategoriesProductEntity>> getAll();
}
