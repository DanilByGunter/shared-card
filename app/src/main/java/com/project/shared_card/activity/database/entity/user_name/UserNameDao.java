package com.project.shared_card.activity.database.entity.user_name;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface UserNameDao {
    @Insert
    void createUser(UserNameEntity user);
    @Query("select * from user_name")
   LiveData<List<UserNameEntity>> findAll();
    @Update
    void update(UserNameEntity entity);
    @Query("select * from user_name where id =-1")
    LiveData<UserNameEntity> getMe();
    @Delete
    void delete(UserNameEntity entity);

}
