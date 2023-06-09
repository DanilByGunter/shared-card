package com.project.shared_card.database.entity.shop.product;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShopProductDao {
    @Insert
    void add(List<ShopProductEntity> entity);
    @Query("select * from shop_product")
    LiveData<List<ShopProductEntity>> getAll();

}
