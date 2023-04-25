package com.project.shared_card.database.entity.shop;

import androidx.room.Dao;
import androidx.room.Insert;

import java.util.List;

@Dao
public interface ShopDao {
    @Insert
    void insertShop(List<ShopEntity> shopEntity);
}
