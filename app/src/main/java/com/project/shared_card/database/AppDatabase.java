package com.project.shared_card.database;



import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.project.shared_card.database.entity.groups.GroupsDao;
import com.project.shared_card.database.entity.users.UsersDao;
import com.project.shared_card.database.entity.CategoriesEntity;
import com.project.shared_card.database.entity.CheckEntity;
import com.project.shared_card.database.entity.GroupEntity;
import com.project.shared_card.database.entity.groups.GroupsEntity;
import com.project.shared_card.database.entity.MetricsEntity;
import com.project.shared_card.database.entity.users.UsersEntity;

@Database(version = 1,entities = {CategoriesEntity.class, CheckEntity.class, GroupEntity.class, GroupsEntity.class, MetricsEntity.class, UsersEntity.class})
public abstract class AppDatabase extends RoomDatabase {
    abstract GroupsDao getGroupsDao();
    abstract UsersDao getUsersDao();
}
