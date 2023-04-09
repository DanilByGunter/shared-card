package com.project.shared_card.database.entity.links;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.database.entity.CategoriesEntity;
import com.project.shared_card.database.entity.CheckEntity;

import java.util.List;

public class CategoryWithChecks {
    @Embedded
    public CategoriesEntity category;
    @Relation(parentColumn = "id", entityColumn = "categoryId")
    public List<CheckEntity> ListCheck;
}

