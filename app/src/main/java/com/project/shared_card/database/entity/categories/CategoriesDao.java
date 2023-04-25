package com.project.shared_card.database.entity.categories;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface CategoriesDao {
    @Insert
    void insertCategory(CategoriesEntity categoriesEntity);
}
