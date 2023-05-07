package com.project.shared_card.database.entity.group.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.group.GroupEntity;

import java.util.List;

public interface GroupRepository {
    void createRepository(GroupEntity entity);
    LiveData<List<GroupEntity>> getAllGroup();
}
