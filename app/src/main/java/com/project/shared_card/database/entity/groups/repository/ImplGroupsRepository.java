package com.project.shared_card.database.entity.groups.repository;

import com.project.shared_card.database.entity.GroupEntity;
import com.project.shared_card.database.entity.groups.GroupsDao;
import com.project.shared_card.database.entity.groups.GroupsEntity;
import com.project.shared_card.database.entity.users.UsersDao;
import com.project.shared_card.model.SignUp;

public class ImplGroupsRepository implements GroupsRepository{
    GroupsDao groupsDao;
    public ImplGroupsRepository(GroupsDao groupsDao) {
        this.groupsDao = groupsDao;
    }

    @Override
    public void createGroups(SignUp group) {
        GroupsEntity entity = GroupsEntity.fromSignUpOfUser(group);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                groupsDao.createGroup(entity);
            }
        });
        thread.start();
    }
}
