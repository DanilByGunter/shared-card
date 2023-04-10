package com.project.shared_card.database.entity.user_name;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.project.shared_card.model.SignUp;

@Entity(tableName = "user_name")
public class UserNameEntity {
    @PrimaryKey()
    private long id;
    private String name;
    private String photo;

    public static UserNameEntity fromSignUpOfUser(SignUp user) {
        UserNameEntity entity = new UserNameEntity(user.getId(), user.getName(), user.getPhoto());
        return entity;
    }
    public static SignUp toSignUpOfUser(UserNameEntity entity){
        SignUp user = new SignUp(entity.getId(), entity.getName(), entity.getPhoto());
        return user;
    }
    public UserNameEntity(long id, String name, String photo) {
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
