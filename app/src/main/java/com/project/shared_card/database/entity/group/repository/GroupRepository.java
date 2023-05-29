package com.project.shared_card.database.entity.group.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.group.GroupEntity;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;
import com.project.shared_card.database.entity.user_name.UserNameEntity;

import java.util.List;

public interface GroupRepository {

    void createGroup(GroupEntity entity);

    void createGroupFromList(Long idGroup, List<UserNameEntity> users, List<Integer> statuses);

    LiveData<List<GroupEntity>> getAllGroup();
}
