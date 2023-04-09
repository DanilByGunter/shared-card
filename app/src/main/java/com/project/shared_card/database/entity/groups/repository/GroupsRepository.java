package com.project.shared_card.database.entity.groups.repository;

import com.project.shared_card.database.entity.GroupEntity;
import com.project.shared_card.model.SignUp;

public interface GroupsRepository {
    void createGroups(SignUp group);
}
