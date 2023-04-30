package com.project.shared_card.database.entity.check.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.check.CheckDao;
import com.project.shared_card.database.entity.check.CheckEntity;
import com.project.shared_card.database.entity.check.FullCheck;

import java.util.List;

public class ImplCheckRepository implements CheckRepository{
    CheckDao checkDao;

    public ImplCheckRepository(CheckDao checkDao) {
        this.checkDao = checkDao;
    }

    @Override
    public LiveData<List<FullCheck>> getAll(Long groupId) {
        return checkDao.getAll(groupId);
    }

    @Override
    public void addCheck(CheckEntity check) {
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                checkDao.addCheck(check);
            }
        });
        thread.start();
    }
}
