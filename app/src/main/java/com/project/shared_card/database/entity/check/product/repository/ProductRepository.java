package com.project.shared_card.database.entity.check.product.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.check.product.FullProduct;
import com.project.shared_card.database.entity.check.product.ProductEntity;

import java.util.List;

public interface ProductRepository {
    LiveData<List<FullProduct>> getAllForCheck(Long groupId);
    LiveData<List<FullProduct>> getAllForCheckQuery(Long groupId,String query);
    LiveData<List<FullProduct>> getAllForHistory(Long groupId);
    void add(ProductEntity check);
    void update(ProductEntity entity);
    LiveData<ProductEntity> get(long id);
    void delete(ProductEntity entity);
}
