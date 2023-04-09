package com.project.shared_card.database.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.dao.UsersDao;
import com.project.shared_card.database.entity.UsersEntity;
import com.project.shared_card.model.SignUpOfUser;

import java.util.List;


public class ImplementationUsersRepository implements UsersRepository{
     private UsersDao usersDao;

    public ImplementationUsersRepository(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public void createUser(SignUpOfUser user) {
        UsersEntity entity = UsersEntity.fromSignUpOfUser(user);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                usersDao.createUser(entity);
            }
        });
        thread.start();
    }

    @Override
    public LiveData<List<UsersEntity>> allUsers() {
        return  usersDao.findAll();
    }

}
