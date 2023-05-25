package com.project.shared_card.database.entity.statistic.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.statistic.StatsDao;
import com.project.shared_card.database.entity.statistic.model.Price;
import com.project.shared_card.database.entity.statistic.model.Stats;

import java.util.List;

public class ImplStatsRepository implements StatsRepository {
    StatsDao dao;

    public ImplStatsRepository(StatsDao dao) {
        this.dao = dao;
    }


    @Override
    public LiveData<List<Stats>> getCategoriesCount(Long days, Long id_user) {
        return dao.getCategoriesCount(days, id_user);
    }

    @Override
    public LiveData<List<Stats>> getGeneralCategoriesCount(Long days, Long id_group) {
        return dao.getGeneralCategoriesCount(days, id_group);
    }

    @Override
    public LiveData<List<Stats>> getShopsCount(Long days, Long id_group) {
        return dao.getShopsCount(days, id_group);
    }

    @Override
    public LiveData<List<Price>> getSpending(Long days, Long id_group) {
        return dao.getSpending(days, id_group);
    }
    @Override
    public LiveData<List<Price>> getSpending(Long days) {
        return dao.getSpending(days);
    }
}
