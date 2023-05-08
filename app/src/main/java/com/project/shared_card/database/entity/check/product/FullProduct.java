package com.project.shared_card.database.entity.check.product;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.activity.main_screen.check.tabs.current.CurrentListFragment;
import com.project.shared_card.database.entity.currency.CurrencyEntity;
import com.project.shared_card.database.entity.shop.product.ShopProductEntity;
import com.project.shared_card.database.entity.categories.product.CategoriesProductEntity;
import com.project.shared_card.database.entity.metrics.MetricsEntity;
import com.project.shared_card.database.entity.user_name.UserNameEntity;

public class FullProduct {
    @Embedded
    public ProductEntity product;
    @Relation(
            parentColumn = "metric_id",
            entityColumn = "id")
    public MetricsEntity metric;
    @Relation(
            parentColumn = "category_id",
            entityColumn = "id")
    public CategoriesProductEntity category;
    @Relation(
            parentColumn = "currency_id",
            entityColumn = "id")
    public CurrencyEntity currency;
    @Relation(
            parentColumn = "shop_id",
            entityColumn = "id")
    public ShopProductEntity shop;
    @Relation(
            parentColumn = "user_name_creator_id",
            entityColumn = "id")
    public UserNameEntity creator;
    @Relation(
            parentColumn = "user_name_buyer_id",
            entityColumn = "id")
    public UserNameEntity buyer;



}
