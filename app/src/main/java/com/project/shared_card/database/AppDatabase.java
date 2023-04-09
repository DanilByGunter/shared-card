package com.project.shared_card.database;



import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.project.shared_card.database.entity.Categories;
import com.project.shared_card.database.entity.Check;
import com.project.shared_card.database.entity.Group;
import com.project.shared_card.database.entity.Groups;
import com.project.shared_card.database.entity.Metrics;
import com.project.shared_card.database.entity.Users;

@Database(version = 1,entities = {Categories.class, Check.class, Group.class, Groups.class, Metrics.class, Users.class})
abstract class AppDatabase extends RoomDatabase {

}
