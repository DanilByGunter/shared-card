package com.project.shared_card.database.entity.user_name.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.user_name.UserNameDao;
import com.project.shared_card.database.entity.user_name.UserNameEntity;
import com.project.shared_card.model.SignUp;

import java.util.List;


public class ImplUserNameRepository implements UserNameRepository {
     private UserNameDao userNameDao;

    public ImplUserNameRepository(UserNameDao userNameDao) {
        this.userNameDao = userNameDao;
    }

    @Override
    public void createUser(SignUp user) {
        UserNameEntity entity = UserNameEntity.fromSignUpOfUser(user);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                userNameDao.createUser(entity);
            }
        });
        thread.start();
    }

    @Override
    public LiveData<List<UserNameEntity>> allUsers() {
        return  userNameDao.findAll();
    }

    @Override
    public void updateMe(String name) {
        UserNameEntity me = new UserNameEntity(-1,name);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                userNameDao.update(me);
            }
        });
        thread.start();
    }

}
