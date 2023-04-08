package com.project.shared_card.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
@Entity(tableName = "id_count")
public class Metrics {
    @PrimaryKey
    public long id;
    public String name;
}
