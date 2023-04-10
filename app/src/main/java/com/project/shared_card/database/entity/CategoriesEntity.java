package com.project.shared_card.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class CategoriesEntity {
    @PrimaryKey
    public long id;
    public String name;
}