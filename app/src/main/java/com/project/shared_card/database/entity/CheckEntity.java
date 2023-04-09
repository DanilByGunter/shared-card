package com.project.shared_card.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "check")
public class CheckEntity {
    @PrimaryKey
    public long id;
    public long categoryId;
    public long metricId;
    public long groupId;
    public long date_first;
    public long date_last;
    public String name_buyer;
    public String name_user;
    public Boolean status;
}
