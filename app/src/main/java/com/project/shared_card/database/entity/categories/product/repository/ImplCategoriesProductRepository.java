package com.project.shared_card.database.entity.categories.product.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.project.shared_card.activity.database.entity.categories.product.CategoriesProductDao;
import com.project.shared_card.activity.database.entity.categories.product.CategoriesProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ImplCategoriesProductRepository implements CategoryProductRepository {

    private CategoriesProductDao dao;

    public ImplCategoriesProductRepository(CategoriesProductDao categoriesProductDao) {
        this.dao = categoriesProductDao;}

    @Override
    public void add(List<CategoriesProductEntity> categoriesProductEntity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                dao.add(categoriesProductEntity);
            }
        });
        thread.start();
    }


    @Override
    public LiveData<List<String>> getAll() {
        LiveData<List<CategoriesProductEntity>> categoriesEntity = dao.getAll();
        return Transformations.map(categoriesEntity, categoriesString -> {
                return categoriesString.stream().map(categoriesEntity1 -> categoriesEntity1.getName()).collect(Collectors.toList());
        });
    }

}
