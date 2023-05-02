package com.project.shared_card.database.entity.currency.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.currency.CurrencyEntity;

import java.util.List;

public interface CurrencyRepository {
    void add(List<CurrencyEntity> entity);

    LiveData<List<String>> getAll();
}
