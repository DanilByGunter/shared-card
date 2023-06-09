package com.project.shared_card.database.entity.metrics.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.metrics.MetricsEntity;

import java.util.List;

public interface MetricsRepository {
    void addMetrics(List<MetricsEntity> metricsEntity);
    LiveData<List<String>> getAll();
}
