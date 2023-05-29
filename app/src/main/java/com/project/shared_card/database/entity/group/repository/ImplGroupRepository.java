package com.project.shared_card.database.entity.group.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.group.GroupDao;
import com.project.shared_card.database.entity.group.GroupEntity;
import com.project.shared_card.database.entity.user_name.UserNameEntity;

import java.util.ArrayList;
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
    public void createGroupFromList(Long idGroup, List<UserNameEntity> users, List<Integer> statuses) {
        List<GroupEntity> groupEntities = new ArrayList<>();
        for(int i=0;i<users.size();i++){
            Boolean status;
            if(statuses.get(i) ==1){
                status=true;
            }
            else{
                status=false;
            }
            groupEntities.add(new GroupEntity(idGroup,users.get(i).getId(),status));
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                groupDao.createGroups(groupEntities);
            }
        });
        thread.start();
    }

    @Override
    public LiveData<List<GroupEntity>> getAllGroup() {
        return groupDao.getAllGroup();
    }

}
