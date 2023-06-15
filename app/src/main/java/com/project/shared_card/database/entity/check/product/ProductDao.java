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
    @Query("select * from product where group_name_id = :id and status != 2 order by status,date_first desc ")
    LiveData<List<FullProduct>> getAllForCheck(long id);
    @Query("select * from product where group_name_id = :id and status != 2 and product_name like :query order by status,product_name asc  ")
    LiveData<List<FullProduct>> getAllForCheckQuery(long id,String query);
    @Query("select * from product where group_name_id = :id and status = 2")
    LiveData<List<FullProduct>> getAllForHistory(long id);
    @Query("select * from product where id = :id")
    LiveData<ProductEntity> get(long id);
    @Insert
    void add(ProductEntity product);
    @Update
    void update(ProductEntity product);
    @Delete
    void delete(ProductEntity product);
}
