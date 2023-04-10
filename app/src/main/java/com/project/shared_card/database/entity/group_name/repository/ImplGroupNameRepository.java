package com.project.shared_card.database.entity.group_name.repository;

import com.project.shared_card.database.entity.group_name.GroupNameDao;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;
import com.project.shared_card.model.SignUp;

public class ImplGroupNameRepository implements GroupNameRepository {
    GroupNameDao groupNameDao;
    public ImplGroupNameRepository(GroupNameDao groupNameDao) {
        this.groupNameDao = groupNameDao;
    }

    @Override
    public void createGroups(SignUp group) {
        GroupNameEntity entity = GroupNameEntity.fromSignUpOfUser(group);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                groupNameDao.createGroup(entity);
            }
        });
        thread.start();
    }
}
