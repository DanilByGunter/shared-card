package com.project.shared_card.database.entity.shop.target.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.project.shared_card.database.entity.shop.product.ShopProductEntity;
import com.project.shared_card.database.entity.shop.target.ShopTargetDao;
import com.project.shared_card.database.entity.shop.target.ShopTargetEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ImplShopTargetRepository implements ShopTargetRepository {
    ShopTargetDao dao;

    public ImplShopTargetRepository(ShopTargetDao dao) {
        this.dao = dao;
    }

    @Override
    public void add(List<ShopTargetEntity> entity) {
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
        LiveData<List<ShopTargetEntity>> shop = dao.getAll();
        return Transformations.map(shop, shopString -> {
            return shopString.stream().map(shopS -> shopS.getName()).collect(Collectors.toList());
        });
    }
}
