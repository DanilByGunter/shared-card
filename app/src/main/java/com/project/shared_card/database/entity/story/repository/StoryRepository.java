package com.project.shared_card.database.entity.story.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.story.model.History;

import java.util.List;

public interface StoryRepository {
    LiveData<List<History>> getAll(Long id_group);

    LiveData<List<History>> getAllForQuery(Long id_group, String query);
}
