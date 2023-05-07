package com.project.shared_card.database.entity.currency;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CurrencyDao {
    @Insert
    void add(List<CurrencyEntity> entity);
    @Query("select * from currency")
    LiveData<List<CurrencyEntity>> getAll();
}
