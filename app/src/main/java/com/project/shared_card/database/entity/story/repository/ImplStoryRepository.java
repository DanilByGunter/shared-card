package com.project.shared_card.database.entity.story.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.story.StoryDao;
import com.project.shared_card.database.entity.story.model.History;

import java.util.List;

public class ImplStoryRepository implements StoryRepository{
    StoryDao dao;

    public ImplStoryRepository(StoryDao dao) {
        this.dao = dao;
    }

    @Override
    public LiveData<List<History>> getAll(Long id_group) {
        return dao.getAll(id_group);
    }

    @Override
    public LiveData<List<History>> getAllForQuery(Long id_group, String query) {
        return dao.getAll(id_group);
    }
}
