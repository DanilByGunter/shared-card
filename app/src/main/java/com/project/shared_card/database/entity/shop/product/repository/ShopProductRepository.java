package com.project.shared_card.database.entity.shop.product.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.shop.product.ShopProductEntity;

import java.util.List;

public interface ShopProductRepository {
    void add(List<ShopProductEntity> shopProductEntity);
   LiveData<List<String>> getAll();
}
