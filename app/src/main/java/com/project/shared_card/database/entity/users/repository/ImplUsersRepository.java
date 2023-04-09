package com.project.shared_card.database.entity.users.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.users.UsersDao;
import com.project.shared_card.database.entity.users.UsersEntity;
import com.project.shared_card.model.SignUp;

import java.util.List;


public class ImplUsersRepository implements UsersRepository{
     private UsersDao usersDao;

    public ImplUsersRepository(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public void createUser(SignUp user) {
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
