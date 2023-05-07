package com.project.shared_card.database.entity.shop.product.repository;

import com.project.shared_card.activity.database.entity.shop.product.ShopProductEntity;

import java.util.List;

public interface ShopProductRepository {
    void add(List<ShopProductEntity> shopProductEntity);
}
