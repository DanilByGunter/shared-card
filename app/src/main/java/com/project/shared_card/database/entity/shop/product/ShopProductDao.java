package com.project.shared_card.database.entity.shop.product;

import androidx.room.Dao;
import androidx.room.Insert;

import java.util.List;

@Dao
public interface ShopProductDao {
    @Insert
    void add(List<ShopProductEntity> entity);

}
