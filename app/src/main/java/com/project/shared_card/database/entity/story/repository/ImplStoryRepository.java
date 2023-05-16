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
    public LiveData<List<History>> getAll() {
        return dao.getAll();
    }
}
