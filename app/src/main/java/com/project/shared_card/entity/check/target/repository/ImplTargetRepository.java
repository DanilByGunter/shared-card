package com.project.shared_card.entity.check.target.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.activity.database.entity.check.target.TargetDao;
import com.project.shared_card.entity.check.target.FullTarget;
import com.project.shared_card.entity.check.target.TargetEntity;

import java.util.List;

public class ImplTargetRepository implements TargetRepository{
    TargetDao targetDao;

    public ImplTargetRepository(TargetDao targetDao) {
        this.targetDao = targetDao;
    }

    @Override
    public void add(TargetEntity entity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                targetDao.add(entity);
            }
        });
        thread.start();
    }

    @Override
    public LiveData<List<FullTarget>> getAll(Long groupId) {
        return targetDao.getAll(groupId);
    }

    @Override
    public void update(TargetEntity entity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                targetDao.update(entity);
            }
        });
        thread.start();
    }

    @Override
    public void delete(TargetEntity entity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                targetDao.delete(entity);
            }
        });
        thread.start();
    }
}
