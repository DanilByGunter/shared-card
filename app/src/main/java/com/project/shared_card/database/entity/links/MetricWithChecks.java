package com.project.shared_card.database.entity.links;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.database.entity.CheckEntity;
import com.project.shared_card.database.entity.MetricsEntity;

import java.util.List;

public class MetricWithChecks {
    @Embedded
    public MetricsEntity metric;
    @Relation(parentColumn = "id",entityColumn = "metricId")
    public List<CheckEntity> ListCheck;
}
