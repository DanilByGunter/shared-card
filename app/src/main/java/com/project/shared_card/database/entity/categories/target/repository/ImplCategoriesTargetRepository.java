package com.project.shared_card.database.entity.categories.target.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.project.shared_card.database.entity.categories.product.CategoriesProductDao;
import com.project.shared_card.database.entity.categories.product.CategoriesProductEntity;
import com.project.shared_card.database.entity.categories.target.CategoriesTargetDao;
import com.project.shared_card.database.entity.categories.target.CategoriesTargetEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ImplCategoriesTargetRepository implements CategoryTargetRepository {

    private CategoriesTargetDao dao;

    public ImplCategoriesTargetRepository(CategoriesTargetDao dao) {
        this.dao = dao;}

    @Override
    public void add(List<CategoriesTargetEntity> entity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                dao.add(entity);
            }
        });
        thread.start();
    }


    @Override
    public LiveData<List<String>> getAll() {
        LiveData<List<CategoriesTargetEntity>> categoriesEntity = dao.getAll();
        return Transformations.map(categoriesEntity, categoriesString -> {
                return categoriesString.stream().map(categoriesEntity1 -> categoriesEntity1.getName()).collect(Collectors.toList());
        });
    }
}
