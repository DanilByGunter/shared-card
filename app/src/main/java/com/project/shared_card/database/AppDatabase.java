package com.project.shared_card.database;



import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.project.shared_card.database.entity.group_name.GroupNameDao;
import com.project.shared_card.database.entity.group_name.GroupNameEntity;
import com.project.shared_card.database.entity.categories.product.CategoriesProductDao;
import com.project.shared_card.database.entity.categories.target.CategoriesTargetDao;
import com.project.shared_card.database.entity.categories.target.CategoriesTargetEntity;
import com.project.shared_card.database.entity.check.product.ProductDao;
import com.project.shared_card.database.entity.check.target.TargetDao;
import com.project.shared_card.database.entity.check.target.TargetEntity;
import com.project.shared_card.database.entity.currency.CurrencyDao;
import com.project.shared_card.database.entity.currency.CurrencyEntity;
import com.project.shared_card.database.entity.group.GroupDao;
import com.project.shared_card.database.entity.metrics.MetricsDao;
import com.project.shared_card.database.entity.shop.product.ShopProductDao;
import com.project.shared_card.database.entity.shop.product.ShopProductEntity;
import com.project.shared_card.database.entity.shop.target.ShopTargetDao;
import com.project.shared_card.database.entity.shop.target.ShopTargetEntity;
import com.project.shared_card.database.entity.user_name.UserNameDao;
import com.project.shared_card.database.entity.user_name.UserNameEntity;
import com.project.shared_card.database.entity.categories.product.CategoriesProductEntity;
import com.project.shared_card.database.entity.check.product.ProductEntity;
import com.project.shared_card.database.entity.group.GroupEntity;
import com.project.shared_card.database.entity.metrics.MetricsEntity;

@Database(version = 1,entities = {
        CategoriesProductEntity.class,
        CategoriesTargetEntity.class,
        CurrencyEntity.class,
        ProductEntity.class,
        TargetEntity.class,
        GroupEntity.class,
        GroupNameEntity.class,
        MetricsEntity.class,
        UserNameEntity.class,
        ShopProductEntity.class,
        ShopTargetEntity.class})
public abstract class AppDatabase extends RoomDatabase {
    abstract CategoriesProductDao getCategoriesProductDao();
    abstract GroupNameDao getGroupNameDao();
    abstract GroupDao getGroupDao();
    abstract UserNameDao getUserNameDao();
    abstract MetricsDao getMetricsDao();
    abstract ShopProductDao getShopProductDao();
    abstract ShopTargetDao getShopTargetDao();
    abstract ProductDao getProductDao();
    abstract TargetDao getTargetDao();
    abstract CategoriesTargetDao getCategoriesTargetDao();
    abstract CurrencyDao getCurrencyDao();
}
