package com.project.shared_card.database.entity.check.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.check.CheckEntity;
import com.project.shared_card.database.entity.check.FullCheck;

import java.util.List;

public interface CheckRepository {
    LiveData<List<FullCheck>> getAll(Long groupId);
    void addCheck(CheckEntity check);
}
