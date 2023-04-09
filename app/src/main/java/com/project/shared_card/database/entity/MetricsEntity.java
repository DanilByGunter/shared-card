package com.project.shared_card.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "id_count")
public class MetricsEntity {
    @PrimaryKey
    public long id;
    public String name;
}
