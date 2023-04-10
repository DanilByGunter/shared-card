package com.project.shared_card.database.entity.group.repository;

import com.project.shared_card.database.entity.group.GroupDao;
import com.project.shared_card.database.entity.group.GroupEntity;

public class ImplGroupRepository implements GroupRepository{
    GroupDao groupDao;

    public ImplGroupRepository(GroupDao groupDao) {
        this.groupDao = groupDao;
    }
    @Override
    public void createRepository(GroupEntity entity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                groupDao.createGroup(entity);
            }
        });
        thread.start();
    }
}
