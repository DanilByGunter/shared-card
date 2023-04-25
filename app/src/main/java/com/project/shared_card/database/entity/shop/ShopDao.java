package com.project.shared_card.database.entity.shop;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface ShopDao {
    @Insert
    void insertShop(ShopEntity shopEntity);
}
