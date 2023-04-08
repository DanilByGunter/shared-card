package com.project.shared_card.database.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class MetricWithChecks {
    @Embedded
    public Metrics metric;
    @Relation(parentColumn = "id",entityColumn = "metricId")
    public List<Check> ListCheck;
}
