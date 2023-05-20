package com.project.shared_card.database;

import android.content.Context;

import androidx.room.Room;

import com.project.shared_card.database.entity.group_name.repository.ImplGroupNameRepository;
import com.project.shared_card.database.entity.categories.product.repository.ImplCategoriesProductRepository;
import com.project.shared_card.database.entity.categories.target.repository.ImplCategoriesTargetRepository;
import com.project.shared_card.database.entity.check.product.repository.ImplProductRepository;
import com.project.shared_card.database.entity.check.target.repository.ImplTargetRepository;
import com.project.shared_card.database.entity.currency.repository.ImplCurrencyRepository;
import com.project.shared_card.database.entity.group.repository.ImplGroupRepository;
import com.project.shared_card.database.entity.metrics.repository.ImplMetricsRepository;
import com.project.shared_card.database.entity.shop.product.repository.ImplShopProductRepository;
import com.project.shared_card.database.entity.shop.target.repository.ImplShopTargetRepository;
import com.project.shared_card.database.entity.statistic.repository.ImplStatsRepository;
import com.project.shared_card.database.entity.story.repository.ImplStoryRepository;
import com.project.shared_card.database.entity.user_name.repository.ImplUserNameRepository;

public class ImplDB {
    private AppDatabase db;

    public ImplDB(Context context) {
        this.db = Room.databaseBuilder(context,
                        AppDatabase.class, "Sample.db")
                .build();
    }

    public AppDatabase getDb() {
        return db;
    }

    public ImplUserNameRepository user_name() {
        return new ImplUserNameRepository(db.getUserNameDao());
    }
    public ImplGroupNameRepository group_name(){
        return new ImplGroupNameRepository(db.getGroupNameDao());
    }
    public ImplGroupRepository group(){
        return new ImplGroupRepository(db.getGroupDao());
    }
    public ImplCategoriesProductRepository category_product(){
        return new ImplCategoriesProductRepository(db.getCategoriesProductDao());
    }
    public ImplShopProductRepository shop_product(){
        return new ImplShopProductRepository(db.getShopProductDao());
    }
    public ImplShopTargetRepository shop_target(){
        return new ImplShopTargetRepository(db.getShopTargetDao());
    }
    public ImplMetricsRepository metric(){
        return new ImplMetricsRepository(db.getMetricsDao());
    }
    public ImplProductRepository product(){
        return new ImplProductRepository(db.getProductDao());
    }
    public ImplTargetRepository target(){
        return new ImplTargetRepository(db.getTargetDao());
    }
    public ImplCategoriesTargetRepository category_target(){
        return new ImplCategoriesTargetRepository(db.getCategoriesTargetDao());
    }
    public ImplStatsRepository stats() {
        return new ImplStatsRepository(db.getStatsDao());
    }
    public ImplCurrencyRepository currency(){
        return  new ImplCurrencyRepository(db.getCurrencyDao());
    }
    public ImplStoryRepository story(){
        return new ImplStoryRepository(db.getStoryDao());
    }
}
