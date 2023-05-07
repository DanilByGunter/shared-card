package com.project.shared_card.database.entity.group_name;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.project.shared_card.model.SignUp;

@Entity(tableName = "group_name")
public class GroupNameEntity {
    @PrimaryKey()
    private long id;
    private String name;

    public static GroupNameEntity fromSignUpOfUser(SignUp group) {
        GroupNameEntity entity = new GroupNameEntity(group.getId(), group.getName());
        return entity;
    }

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
