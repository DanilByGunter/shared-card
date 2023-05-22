package com.project.shared_card.database.entity.group_name;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "group_name")
public class GroupNameEntity {
    @PrimaryKey()
    private long id;
    private String name;



    public GroupNameEntity(long id, String name) {
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
