package com.project.shared_card.database;



import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.project.shared_card.database.dao.CheckDao;
import com.project.shared_card.database.dao.UsersDao;
import com.project.shared_card.database.entity.CategoriesEntity;
import com.project.shared_card.database.entity.CheckEntity;
import com.project.shared_card.database.entity.GroupEntity;
import com.project.shared_card.database.entity.GroupsEntity;
import com.project.shared_card.database.entity.MetricsEntity;
import com.project.shared_card.database.entity.UsersEntity;

import java.util.List;

@Database(version = 1,entities = {CategoriesEntity.class, CheckEntity.class, GroupEntity.class, GroupsEntity.class, MetricsEntity.class, UsersEntity.class})
public abstract class AppDatabase extends RoomDatabase {
    abstract CheckDao getCheckDao();
    abstract UsersDao getUsersDao();
}
