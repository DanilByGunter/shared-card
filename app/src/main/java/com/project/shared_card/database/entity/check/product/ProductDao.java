package com.project.shared_card.database.entity.check.product;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("select * from product where group_name_id = :id order by status")
    LiveData<List<FullProduct>> getAll(long id);
    @Insert
    void add(ProductEntity product);
    @Update
    void update(ProductEntity product);
    @Query("select * from product where id = :id")
    LiveData<ProductEntity> get(long id);
    @Delete
    void delete(ProductEntity product);
}
