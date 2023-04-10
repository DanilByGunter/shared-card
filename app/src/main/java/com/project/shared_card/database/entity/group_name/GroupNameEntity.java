package com.project.shared_card.database.entity.group_name;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.project.shared_card.model.SignUp;

@Entity(tableName = "group_name")
public class GroupNameEntity {
    @PrimaryKey()
    private long id;
    private String name;
    private String photo;

    public static GroupNameEntity fromSignUpOfUser(SignUp group) {
        GroupNameEntity entity = new GroupNameEntity(group.getId(), group.getName(), group.getPhoto());
        return entity;
    }

    public GroupNameEntity(long id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
