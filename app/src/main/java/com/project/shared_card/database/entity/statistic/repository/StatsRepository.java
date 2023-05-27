package com.project.shared_card.database.entity.statistic.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.statistic.model.Price;
import com.project.shared_card.database.entity.statistic.model.Stats;

import java.util.List;

public interface StatsRepository {
    LiveData<List<Stats>> getCategoriesStats(Long days, Long id_user, Long id_group);
    LiveData<List<Stats>> getShopStats(Long days, Long id_group);
    LiveData<List<Stats>> getGeneralCategoriesCount(Long days, Long id_group);
    LiveData<List<Price>> getSpending(Long days, Long id_group);
    LiveData<List<Price>> getSpending(Long days);

}
