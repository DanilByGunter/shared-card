package com.project.shared_card.activity.database.entity.shop.product;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shop_product")
public class ShopProductEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    public ShopProductEntity(String name) {
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
