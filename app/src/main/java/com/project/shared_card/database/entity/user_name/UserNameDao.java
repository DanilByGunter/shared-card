package com.project.shared_card.database.entity.user_name;

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
    @Insert
    void createUsers(List<UserNameEntity> users);
    @Query("select * from user_name")
   LiveData<List<UserNameEntity>> findAll();
    @Query("select * from user_name where id =-1")
    LiveData<UserNameEntity> getMe();
    @Update
    void update(UserNameEntity entity);
    @Delete
    void delete(UserNameEntity entity);

}
