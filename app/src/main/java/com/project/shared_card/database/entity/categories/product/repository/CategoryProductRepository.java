package com.project.shared_card.database.entity.categories.product.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.activity.database.entity.categories.product.CategoriesProductEntity;

import java.util.List;

public interface CategoryProductRepository {
    void add(List<CategoriesProductEntity> entity);
    LiveData<List<String>> getAll();
}
