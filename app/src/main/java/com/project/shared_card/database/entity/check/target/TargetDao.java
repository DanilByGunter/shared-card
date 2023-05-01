package com.project.shared_card.database.entity.check.target;

import androidx.room.Dao;
import androidx.room.Insert;

import com.project.shared_card.database.entity.check.product.ProductEntity;

@Dao
public interface TargetDao {
    @Insert
    void add(TargetEntity target);
}
