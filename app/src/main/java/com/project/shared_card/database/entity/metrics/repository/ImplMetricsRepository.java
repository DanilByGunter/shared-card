package com.project.shared_card.database.entity.metrics.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.project.shared_card.database.entity.metrics.MetricsDao;
import com.project.shared_card.database.entity.metrics.MetricsEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ImplMetricsRepository implements MetricsRepository {
    MetricsDao metricsDao;

    public ImplMetricsRepository(MetricsDao metricsDao) {
        this.metricsDao = metricsDao;
    }

    @Override
    public void addMetrics(List<MetricsEntity> metricsEntity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                metricsDao.createMetric(metricsEntity);
            }
        });
        thread.start();
    }

    @Override
    public LiveData<List<String>> getAll() {
        LiveData<List<MetricsEntity>> metricsEntity = metricsDao.getAll();

        return Transformations.map(metricsEntity, metricsString ->{
            return metricsString.stream().map(metricsEntity1 -> metricsEntity1.getName()).collect(Collectors.toList());
        });
    }
}
