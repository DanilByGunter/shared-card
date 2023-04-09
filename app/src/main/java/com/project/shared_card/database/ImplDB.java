package com.project.shared_card.database;

import android.content.Context;

import androidx.room.Room;

import com.project.shared_card.database.entity.groups.repository.ImplGroupsRepository;
import com.project.shared_card.database.entity.users.repository.ImplUsersRepository;

public class ImplDB {
    private AppDatabase db;

    public ImplDB(Context context) {
        this.db = Room.databaseBuilder(context,
                        AppDatabase.class, "Sample.db")
                .build();
    }

    public AppDatabase getDb() {
        return db;
    }

    public ImplUsersRepository getUsersRepository() {
        return new ImplUsersRepository(db.getUsersDao());
    }
    public ImplGroupsRepository getGroupsRepository(){
        return new ImplGroupsRepository(db.getGroupsDao());
    }
}
