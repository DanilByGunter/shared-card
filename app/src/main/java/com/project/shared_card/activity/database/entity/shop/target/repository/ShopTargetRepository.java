package com.project.shared_card.activity.database.entity.shop.target.repository;

import com.project.shared_card.activity.database.entity.shop.target.ShopTargetEntity;

import java.util.List;

public interface ShopTargetRepository {
    void add(List<ShopTargetEntity> shopProductEntity);
}
