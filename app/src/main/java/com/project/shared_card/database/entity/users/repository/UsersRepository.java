package com.project.shared_card.database.entity.users.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.users.UsersEntity;
import com.project.shared_card.model.SignUp;

import java.util.List;

public interface UsersRepository {
    void createUser(SignUp user);
    LiveData<List<UsersEntity>> allUsers();
}
