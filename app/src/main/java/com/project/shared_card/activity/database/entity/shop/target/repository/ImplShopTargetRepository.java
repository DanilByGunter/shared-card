package com.project.shared_card.activity.database.entity.shop.target.repository;

import com.project.shared_card.activity.database.entity.shop.target.ShopTargetDao;
import com.project.shared_card.activity.database.entity.shop.target.ShopTargetEntity;

import java.util.List;

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
}
