package com.project.shared_card.database.entity.group.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.group.GroupDao;
import com.project.shared_card.database.entity.group.GroupEntity;

import java.util.List;

public class ImplGroupRepository implements GroupRepository{
    GroupDao groupDao;

    public ImplGroupRepository(GroupDao groupDao) {
        this.groupDao = groupDao;
    }
    @Override
    public void createGroup(GroupEntity entity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                groupDao.createGroup(entity);
            }
        });
        thread.start();
    }

    @Override
    public LiveData<List<GroupEntity>> getAllGroup() {
        return groupDao.getAllGroup();
    }

}
