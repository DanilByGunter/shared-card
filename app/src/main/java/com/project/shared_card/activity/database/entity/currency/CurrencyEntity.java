package com.project.shared_card.activity.database.entity.currency;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "currency")
public class CurrencyEntity {
    @PrimaryKey(autoGenerate = true)
    long id;
    String name;

    public CurrencyEntity(String name) {
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
