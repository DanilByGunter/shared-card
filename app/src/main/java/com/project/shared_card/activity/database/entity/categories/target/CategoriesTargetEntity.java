package com.project.shared_card.activity.database.entity.categories.target;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_target")
public class CategoriesTargetEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    public CategoriesTargetEntity(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
