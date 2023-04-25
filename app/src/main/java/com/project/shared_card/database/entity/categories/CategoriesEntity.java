package com.project.shared_card.database.entity.categories;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class CategoriesEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private Boolean status;
    public CategoriesEntity(long id, String name, Boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
