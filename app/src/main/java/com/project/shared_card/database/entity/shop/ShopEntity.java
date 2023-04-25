package com.project.shared_card.database.entity.shop;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shop")
public class ShopEntity {
    @PrimaryKey()
    private long id;
    private String name;

    public ShopEntity(long id, String name) {
        this.id = id;
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
