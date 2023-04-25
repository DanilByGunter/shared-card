package com.project.shared_card.database.entity.shop.repository;

import com.project.shared_card.database.entity.shop.ShopEntity;

import java.util.List;

public interface ShopRepository {
    void addShop(List<ShopEntity> shopEntity);
}
