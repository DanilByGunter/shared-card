package com.project.shared_card.database;

import android.content.Context;

import androidx.room.Room;
import com.project.shared_card.database.entity.group.repository.ImplGroupRepository;
import com.project.shared_card.database.entity.group_name.repository.ImplGroupNameRepository;
import com.project.shared_card.database.entity.user_name.repository.ImplUserNameRepository;

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

    public ImplUserNameRepository getUserNameRepository() {
        return new ImplUserNameRepository(db.getUserNameDao());
    }
    public ImplGroupNameRepository getGroupNameRepository(){
        return new ImplGroupNameRepository(db.getGroupNameDao());
    }
    public ImplGroupRepository getGroupRepository(){
        return new ImplGroupRepository(db.getGroupDao());
    }
}
