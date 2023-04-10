package com.project.shared_card.database.entity.links;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.database.entity.categories.CategoriesEntity;
import com.project.shared_card.database.entity.check.CheckEntity;

import java.util.List;

public class CategoryWithChecks {
    @Embedded
    public CategoriesEntity category;
    @Relation(parentColumn = "id", entityColumn = "categoryId")
    public List<CheckEntity> ListCheck;
}

