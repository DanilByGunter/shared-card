package com.project.shared_card.database.entity.categories.target.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.categories.product.CategoriesProductEntity;
import com.project.shared_card.database.entity.categories.target.CategoriesTargetEntity;

import java.util.List;

public interface CategoryTargetRepository {
    void add(List<CategoriesTargetEntity> entity);
    LiveData<List<String>> getAll();
}
