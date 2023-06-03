package com.project.shared_card.database.entity.user_name.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.user_name.UserNameEntity;

import java.util.List;

public interface UserNameRepository {
    void createUser(UserNameEntity user);
    void createUsers(List<UserNameEntity> user);
    LiveData<List<UserNameEntity>> allUsers();

    void updateMe(String name, byte[] photo);

    LiveData<UserNameEntity> getMe();

    void delete(UserNameEntity entity);
}
