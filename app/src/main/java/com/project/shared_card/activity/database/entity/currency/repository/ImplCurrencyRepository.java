package com.project.shared_card.activity.database.entity.currency.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.project.shared_card.activity.database.entity.currency.CurrencyDao;
import com.project.shared_card.activity.database.entity.currency.CurrencyEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ImplCurrencyRepository implements CurrencyRepository{
    CurrencyDao dao;

    public ImplCurrencyRepository(CurrencyDao dao) {
        this.dao = dao;
    }

    @Override
    public void add(List<CurrencyEntity> entity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                dao.add(entity);
            }
        });
        thread.start();
    }

    @Override
    public LiveData<List<String>> getAll() {
        LiveData<List<CurrencyEntity>> entity = dao.getAll();
        return Transformations.map(entity, currencyString -> {
            return currencyString.stream().map(entity1 -> entity1.getName()).collect(Collectors.toList());
        });
    }
}
