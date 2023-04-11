package com.project.shared_card.database;



import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.project.shared_card.database.entity.group.GroupDao;
import com.project.shared_card.database.entity.group_name.GroupNameDao;
import com.project.shared_card.database.entity.user_name.UserNameDao;
import com.project.shared_card.database.entity.user_name.UserNameEntity;
import com.project.shared_card.database.entity.categories.CategoriesEntity;
import com.project.shared_card.database.entity.check.CheckEntity;
import com.project.shared_card.database.entity.group.GroupEntity;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;
import com.project.shared_card.database.entity.metrics.MetricsEntity;

@Database(version = 1,entities = {CategoriesEntity.class, CheckEntity.class, GroupEntity.class, GroupNameEntity.class, MetricsEntity.class, UserNameEntity.class})
public abstract class AppDatabase extends RoomDatabase {
    abstract GroupNameDao getGroupNameDao();
    abstract GroupDao getGroupDao();
    abstract UserNameDao getUserNameDao();
}
