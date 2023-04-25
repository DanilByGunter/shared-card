package com.project.shared_card.database.entity.categories.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.project.shared_card.database.entity.categories.CategoriesDao;
import com.project.shared_card.database.entity.categories.CategoriesEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ImplCategoriesRepository implements CategoryRepository{

    private CategoriesDao categoriesDao;

    public ImplCategoriesRepository(CategoriesDao categoriesDao) {
        this.categoriesDao = categoriesDao;}

    @Override
    public void add(List<CategoriesEntity> categoriesEntity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                categoriesDao.insertCategory(categoriesEntity);
            }
        });
        thread.start();
    }


    @Override
    public LiveData<List<String>> getforPorduct() {
        LiveData<List<CategoriesEntity>> categoriesEntity = categoriesDao.getForProduct();
        return Transformations.map(categoriesEntity, categoriesString -> {
                return categoriesString.stream().map(categoriesEntity1 -> categoriesEntity1.getName()).collect(Collectors.toList());
        });
    }

    @Override
    public LiveData<List<String>> getForTarget() {
        LiveData<List<CategoriesEntity>> categoriesEntity = categoriesDao.getForTarget();
        return Transformations.map(categoriesEntity, categoriesString -> {
            return categoriesString.stream().map(categoriesEntity1 -> categoriesEntity1.getName()).collect(Collectors.toList());
        });
    }
}
