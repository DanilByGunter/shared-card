package com.project.shared_card.database.entity.metrics.repository;

import com.project.shared_card.database.entity.metrics.MetricsDao;
import com.project.shared_card.database.entity.metrics.MetricsEntity;

public class ImplMetricsRepository implements MetricsRepository {
    MetricsDao metricsDao;

    public ImplMetricsRepository(MetricsDao metricsDao) {
        this.metricsDao = metricsDao;
    }

    @Override
    public void addMetrics(MetricsEntity metricsEntity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                metricsDao.createMetric(metricsEntity);
            }
        });
        thread.start();
    }
}
