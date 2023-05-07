package com.project.shared_card.database.entity.shop.product.repository;

import com.project.shared_card.activity.database.entity.shop.product.ShopProductDao;
import com.project.shared_card.activity.database.entity.shop.product.ShopProductEntity;

import java.util.List;

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
}
