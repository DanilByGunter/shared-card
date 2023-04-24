package com.project.shared_card.database.entity.categories;

import androidx.room.Dao;
import androidx.room.Insert;

import com.project.shared_card.retrofit.model.Category;

import retrofit2.http.GET;

@Dao
public interface CategoriesDao {
    @Insert
    void insertCategory(CategoriesEntity categoriesEntity);
}
