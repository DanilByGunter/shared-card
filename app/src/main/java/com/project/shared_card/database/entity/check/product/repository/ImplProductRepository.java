package com.project.shared_card.database.entity.check.product.repository;

import androidx.lifecycle.LiveData;

import com.project.shared_card.database.entity.check.product.ProductDao;
import com.project.shared_card.database.entity.check.product.ProductEntity;
import com.project.shared_card.database.entity.check.product.FullProduct;

import java.util.List;

public class ImplProductRepository implements ProductRepository {
    ProductDao productDao;

    public ImplProductRepository(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public LiveData<List<FullProduct>> getAllForCheck(Long groupId) {
        return productDao.getAllForCheck(groupId);
    }

    @Override
    public LiveData<List<FullProduct>> getAllForCheckQuery(Long groupId, String query) {
        return productDao.getAllForCheckQuery(groupId,query);
    }

    @Override
    public LiveData<List<FullProduct>> getAllForHistory(Long groupId) {
        return productDao.getAllForHistory(groupId);
    }

    @Override
    public void add(ProductEntity check) {
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                productDao.add(check);
            }
        });
        thread.start();
    }

    @Override
    public void update(ProductEntity entity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                productDao.update(entity);
            }
        });
        thread.start();
    }

    @Override
    public LiveData<ProductEntity> get(long id) {
        return productDao.get(id);
    }

    @Override
    public void delete(ProductEntity product) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                productDao.delete(product);
            }
        });
        thread.start();
    }
}
