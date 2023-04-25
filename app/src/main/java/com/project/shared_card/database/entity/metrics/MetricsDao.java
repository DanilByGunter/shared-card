package com.project.shared_card.database.entity.metrics;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface MetricsDao {
    @Insert
    void createMetric(MetricsEntity metricsEntity);
}
