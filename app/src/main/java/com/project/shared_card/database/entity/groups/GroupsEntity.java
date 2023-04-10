package com.project.shared_card.database.entity.groups;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.project.shared_card.database.entity.GroupEntity;
import com.project.shared_card.database.entity.users.UsersEntity;
import com.project.shared_card.model.SignUp;

import java.sql.Blob;

@Entity(tableName = "groups")
public class GroupsEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String photo;

    public static GroupsEntity fromSignUpOfUser(SignUp group) {
        GroupsEntity entity = new GroupsEntity(0,group.getName(), group.getPhoto());
        return entity;
    }

    public GroupsEntity(long id, String name, String photo) {
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