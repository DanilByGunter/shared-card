package com.project.shared_card.database.entity.shop.target;

import androidx.room.Dao;
import androidx.room.Insert;

import java.util.List;

@Dao
public interface ShopTargetDao {
    @Insert
    void add(List<ShopTargetEntity> entity);

}
