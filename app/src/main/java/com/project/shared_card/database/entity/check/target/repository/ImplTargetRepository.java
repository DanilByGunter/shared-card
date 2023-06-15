package com.project.shared_card.database.entity.check.target.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.check.product.FullProduct;
import com.project.shared_card.database.entity.check.target.TargetDao;
import com.project.shared_card.database.entity.check.target.FullTarget;
import com.project.shared_card.database.entity.check.target.TargetEntity;

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
    public LiveData<List<FullTarget>> getAllForCheck(Long groupId) {
        return targetDao.getAllForCheck(groupId);
    }

    @Override
    public LiveData<List<FullTarget>> getAllForCheckQuery(Long groupId, String query) {
        return targetDao.getAllForCheckQuery(groupId,query);
    }

    @Override
    public LiveData<List<FullTarget>> getAllForHistory(Long groupId) {
        return targetDao.getAllForHistory(groupId);
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
