package com.project.shared_card.database.entity.group_name.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.group_name.AllGroups;
import com.project.shared_card.database.entity.group_name.GroupNameDao;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;
import com.project.shared_card.model.SignUp;

import java.util.List;

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

    @Override
    public LiveData<List<AllGroups>> getAllGroups() {
        return groupNameDao.getALLGroup();
    }

    @Override
    public void updateMe(String name) {
        GroupNameEntity me = new GroupNameEntity(-1,name);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                groupNameDao.update(me);
            }
        });
        thread.start();
    }

    @Override
    public void updateForId(long id,String name) {
        GroupNameEntity entity = new GroupNameEntity(id,name);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                groupNameDao.update(entity);
            }
        });
        thread.start();
    }

    @Override
    public LiveData<GroupNameEntity> getGroupById(Long id) {
        return groupNameDao.getGroupById(id);
    }
}
