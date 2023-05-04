package com.project.shared_card.activity.database.entity.metrics;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MetricsDao {
    @Insert
    void createMetric(List<MetricsEntity> metricsEntity);
    @Query("Select * from metric")
    LiveData<List<MetricsEntity>> getAll();
}
