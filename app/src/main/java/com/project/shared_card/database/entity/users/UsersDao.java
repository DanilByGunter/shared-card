package com.project.shared_card.database.entity.users;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface UsersDao {
    @Insert
    void createUser(UsersEntity user);
    @Query("select * from users")
   LiveData<List<UsersEntity>> findAll();

}
