package com.project.shared_card.database;

import android.content.Context;

import androidx.room.Room;

import com.project.shared_card.database.repository.ImplementationUsersRepository;

public class ImplDB {
    private AppDatabase db;
    private ImplementationUsersRepository usersRepository;

    public ImplDB(Context context) {
        this.db = Room.databaseBuilder(context,
                        AppDatabase.class, "Sample.db")
                .build();
        usersRepository = new ImplementationUsersRepository(db.getUsersDao());
    }

    public AppDatabase getDb() {
        return db;
    }

    public ImplementationUsersRepository getUsersRepository() {
        return usersRepository;
    }
}
