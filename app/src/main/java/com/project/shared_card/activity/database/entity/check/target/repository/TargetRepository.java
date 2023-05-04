package com.project.shared_card.activity.database.entity.check.target.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.activity.database.entity.check.target.FullTarget;
import com.project.shared_card.activity.database.entity.check.target.TargetEntity;

import java.util.List;

public interface TargetRepository {
    void add(TargetEntity entity);
    LiveData<List<FullTarget>> getAll(Long groupId);
    void update(TargetEntity entity);
}