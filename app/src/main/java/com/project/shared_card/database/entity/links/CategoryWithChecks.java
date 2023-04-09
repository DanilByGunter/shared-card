package com.project.shared_card.database.entity.links;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.database.entity.Categories;
import com.project.shared_card.database.entity.Check;

import java.util.List;

public class CategoryWithChecks {
    @Embedded
    public Categories category;
    @Relation(parentColumn = "id", entityColumn = "categoryId")
    public List<Check> ListCheck;
}

