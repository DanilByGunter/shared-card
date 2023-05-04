package com.project.shared_card.activity.database.entity.categories.product;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_product")
public class CategoriesProductEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    public CategoriesProductEntity(String name) {
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
