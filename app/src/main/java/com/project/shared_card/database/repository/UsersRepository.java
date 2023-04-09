package com.project.shared_card.database.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.UsersEntity;
import com.project.shared_card.model.SignUpOfUser;

import java.util.List;

public interface UsersRepository {
    void createUser(SignUpOfUser user);
    LiveData<List<UsersEntity>> allUsers();
}
