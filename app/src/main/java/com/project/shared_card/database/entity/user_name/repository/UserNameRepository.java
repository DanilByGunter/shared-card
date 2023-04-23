package com.project.shared_card.database.entity.user_name.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.user_name.UserNameEntity;
import com.project.shared_card.model.SignUp;

import java.util.List;

public interface UserNameRepository {
    void createUser(SignUp user);
    LiveData<List<UserNameEntity>> allUsers();
    void updateMe(String name);
}
