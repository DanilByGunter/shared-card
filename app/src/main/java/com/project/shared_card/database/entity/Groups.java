package com.project.shared_card.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "groups")
public class Groups {
    @PrimaryKey
    public long id;
    public String name;
    public String photo;
}
