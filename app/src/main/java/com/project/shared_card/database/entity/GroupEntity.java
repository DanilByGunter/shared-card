package com.project.shared_card.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "group")
public class GroupEntity {
    @PrimaryKey
    public long id;
    public long groupsId;
}
