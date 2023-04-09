package com.project.shared_card.database.entity.links;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.database.entity.Check;
import com.project.shared_card.database.entity.Metrics;

import java.util.List;

public class MetricWithChecks {
    @Embedded
    public Metrics metric;
    @Relation(parentColumn = "id",entityColumn = "metricId")
    public List<Check> ListCheck;
}
