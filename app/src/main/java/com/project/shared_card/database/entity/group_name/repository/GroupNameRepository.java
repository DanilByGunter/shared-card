package com.project.shared_card.database.entity.group_name.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.group_name.AllGroups;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;
import com.project.shared_card.model.SignUp;

import java.util.List;

public interface GroupNameRepository {
    void createGroups(SignUp group);
    LiveData<List<AllGroups>> getAllGroups();
    void updateMe(String name);
    void updateForId(long id,String name);
    LiveData<GroupNameEntity> getGroupById(Long id);
}
