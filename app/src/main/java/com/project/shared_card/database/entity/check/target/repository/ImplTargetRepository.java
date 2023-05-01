package com.project.shared_card.database.entity.check.target.repository;

import com.project.shared_card.database.entity.check.target.TargetDao;
import com.project.shared_card.database.entity.check.target.TargetEntity;

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
    }
}
