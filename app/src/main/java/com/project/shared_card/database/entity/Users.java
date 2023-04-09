package com.project.shared_card.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class Users {
    @PrimaryKey
    public long id;
    public String name;
    public String photo;
    public long groupId;
}
