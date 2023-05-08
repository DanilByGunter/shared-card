package com.project.shared_card.database.entity.shop.product.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.project.shared_card.database.entity.categories.product.CategoriesProductEntity;
import com.project.shared_card.database.entity.shop.product.ShopProductDao;
import com.project.shared_card.database.entity.shop.product.ShopProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ImplShopProductRepository implements ShopProductRepository {
    ShopProductDao shopProductDao;

    public ImplShopProductRepository(ShopProductDao shopProductDao) {
        this.shopProductDao = shopProductDao;
    }

    @Override
    public void add(List<ShopProductEntity> shopProductEntity) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                shopProductDao.add(shopProductEntity);
            }
        });
        thread.start();
    }

    @Override
    public LiveData<List<String>> getAll() {
        LiveData<List<ShopProductEntity>> shop = shopProductDao.getAll();
        return Transformations.map(shop, shopString -> {
            return shopString.stream().map(shopS -> shopS.getName()).collect(Collectors.toList());
        });
    }


}
