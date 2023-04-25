package com.project.shared_card.database.entity.categories.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.categories.CategoriesEntity;

import java.util.List;

public interface CategoryRepository {
    void add(List<CategoriesEntity> categoriesEntity);
    LiveData<List<String>> getforPorduct();
    LiveData<List<String>> getForTarget();
}
