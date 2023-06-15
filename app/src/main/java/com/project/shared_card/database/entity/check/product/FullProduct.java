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


    public ProductEntity getProduct() {
        return product;
    }
    public Long getDateLast() {
        return product.getDateLast();
    }
    public String getProductName() {
        return product.getProductName();
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public MetricsEntity getMetric() {
        return metric;
    }

    public void setMetric(MetricsEntity metric) {
        this.metric = metric;
    }

    public CategoriesProductEntity getCategory() {
        return category;
    }
    public String getCategoryName() {
        return category.getName();
    }

    public void setCategory(CategoriesProductEntity category) {
        this.category = category;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public ShopProductEntity getShop() {
        return shop;
    }

    public void setShop(ShopProductEntity shop) {
        this.shop = shop;
    }

    public UserNameEntity getCreator() {
        return creator;
    }

    public void setCreator(UserNameEntity creator) {
        this.creator = creator;
    }

    public UserNameEntity getBuyer() {
        return buyer;
    }
    public String getBuyerName() {
        return buyer.getName();
    }

    public void setBuyer(UserNameEntity buyer) {
        this.buyer = buyer;
    }
}
