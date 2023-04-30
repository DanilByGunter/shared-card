package com.project.shared_card.activity.converter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.project.shared_card.activity.main_screen.check.tabs.current.model.Check;
import com.project.shared_card.database.entity.categories.CategoriesEntity;
import com.project.shared_card.database.entity.check.CheckEntity;
import com.project.shared_card.database.entity.check.FullCheck;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ModelConverter {
    public static List<Check> FromCheckEntityToCheckModel(List<FullCheck> check){
        List<Check> checks = new ArrayList<>();
        for (FullCheck item:check) {
            checks.add(new Check(
                    item.check.getProductName(),
                    item.category.getName(),
                    item.check.getProductCount(),
                    DateConverter.FromLongDateToLocalDateTime(item.check.getDateFirst()),
                    item.creator.getName(),
                    item.metric.getName()));
        }
        return checks;
    }
}
