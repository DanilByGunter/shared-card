package com.project.shared_card.database.entity.check.target;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.project.shared_card.database.entity.check.product.FullProduct;

import java.util.List;

@Dao
public interface TargetDao {
    @Insert
    void add(TargetEntity target);
    @Query("select * from target where group_name_id = :id and status = 2 order by status")
    LiveData<List<FullTarget>> getAllForHistory(long id);
    @Query("select * from target where group_name_id = :id and status != 2 order by status,date_first desc")
    LiveData<List<FullTarget>> getAllForCheck(long id);
    @Query("select * from target where group_name_id = :id and status != 2 and target_name like :query order by status,target_name asc")
    LiveData<List<FullTarget>> getAllForCheckQuery(long id, String query);
    @Update
    void update(TargetEntity entity);
    @Delete
    void delete(TargetEntity entity);
}
