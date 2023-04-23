package com.project.shared_card.database.entity.user_name;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
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

}
