package com.project.shared_card.database.entity.shop.target;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.shared_card.database.entity.shop.product.ShopProductEntity;

import java.util.List;

@Dao
public interface ShopTargetDao {
    @Insert
    void add(List<ShopTargetEntity> entity);
    @Query("select * from shop_target")
    LiveData<List<ShopTargetEntity>> getAll();

}
