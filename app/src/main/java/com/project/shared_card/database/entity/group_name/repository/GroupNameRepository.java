package com.project.shared_card.database.entity.group_name.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.group_name.AllGroups;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;

import java.util.List;

public interface GroupNameRepository {
    void createGroup(GroupNameEntity groupName);
    void createGroups(List<GroupNameEntity> groupNames);
    LiveData<List<AllGroups>> getAllGroups();
    void updateMe(String name,byte[] photo);
    void updateForId(long id,String name,byte[] photo);
    LiveData<GroupNameEntity> getGroupById(Long id);
}
