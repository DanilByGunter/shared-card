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
    public LiveData<List<Stats>> getGeneralCategoriesCount(Long days, Long id_group) {
        return dao.getGeneralCategoriesCount(days, id_group);
    }

    @Override
    public LiveData<List<Price>> getSpending(Long days, Long id_group) {
        return dao.getSpending(days, id_group);
    }
    @Override
    public LiveData<List<Price>> getSpending(Long days) {
        return dao.getSpending(days);
    }

    @Override
    public LiveData<List<Stats>> getCategoriesStats(Long days, Long id_user, Long id_group) {
        return dao.getCategoriesStats(days, id_user, id_group);
    }

    @Override
    public LiveData<List<Stats>> getShopStats(Long days, Long id_group) {
        return dao.getShopStats(days, id_group);
    }
}
