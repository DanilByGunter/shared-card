package com.project.shared_card.activity.database.entity.check.product.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.activity.database.entity.check.product.FullProduct;
import com.project.shared_card.activity.database.entity.check.product.ProductEntity;

import java.util.List;

public interface ProductRepository {
    LiveData<List<FullProduct>> getAll(Long groupId);
    void add(ProductEntity check);
    void update(ProductEntity entity);
    LiveData<ProductEntity> get(long id);
    void delete(ProductEntity product);
}
