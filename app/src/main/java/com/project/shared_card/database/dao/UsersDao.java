package com.project.shared_card.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.shared_card.database.entity.UsersEntity;

import java.util.List;

import io.reactivex.Completable;


@Dao
public interface UsersDao {
    @Insert
    void createUser(UsersEntity user);
    @Query("select * from users")
   LiveData<List<UsersEntity>> findAll();

}
