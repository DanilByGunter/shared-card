package com.project.shared_card.database.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class CategoryWithChecks {
    @Embedded
    public Categories category;
    @Relation(parentColumn = "id", entityColumn = "categoryId")
    public List<Check> ListCheck;
}

