package com.project.shared_card.database.entity.shop.target;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shop_target")
public class ShopTargetEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    public ShopTargetEntity(String name) {
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
