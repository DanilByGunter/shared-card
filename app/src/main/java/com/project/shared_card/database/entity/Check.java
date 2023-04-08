package com.project.shared_card.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "check")
public class Check {
    @PrimaryKey
    public long id;
    public long categoryId;
    public long metricId;
    public long groupId;
    public Date date_first;
    public Date date_last;
    public String name_buyer;
    public String name_user;
    public Boolean status;
}
