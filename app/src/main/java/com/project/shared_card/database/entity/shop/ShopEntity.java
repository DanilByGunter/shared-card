package com.project.shared_card.database.entity.shop;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shop")
public class ShopEntity {
    @PrimaryKey()
    private long id;
    private String name;
    private Boolean status;
    public ShopEntity(long id, String name, Boolean status) {
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
