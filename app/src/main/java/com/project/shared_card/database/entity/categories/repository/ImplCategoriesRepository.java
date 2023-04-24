package com.project.shared_card.database.entity.categories.repository;

import com.project.shared_card.database.entity.categories.CategoriesDao;
import com.project.shared_card.database.entity.categories.CategoriesEntity;

public class ImplCategoriesRepository implements CategoryRepository{

    private CategoriesDao categoriesDao;

    public ImplCategoriesRepository(CategoriesDao categoriesDao) {
        this.categoriesDao = categoriesDao;}

    @Override
    public void addCategory(CategoriesEntity categoriesEntity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                categoriesDao.insertCategory(categoriesEntity);
            }
        });
        thread.start();
    }
}
