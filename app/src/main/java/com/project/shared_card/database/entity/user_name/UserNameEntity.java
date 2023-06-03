package com.project.shared_card.database.entity.user_name;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "user_name")
public class UserNameEntity {
    @PrimaryKey()
    private long id;
    private String name;

    private byte[] photo;
    public UserNameEntity(long id, String name,byte[] photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
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

    }
