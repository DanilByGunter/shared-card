package com.project.shared_card.database.entity.check.target;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.project.shared_card.database.entity.currency.CurrencyEntity;
import com.project.shared_card.database.entity.shop.target.ShopTargetEntity;
import com.project.shared_card.database.entity.categories.target.CategoriesTargetEntity;
import com.project.shared_card.database.entity.user_name.UserNameEntity;

public class FullTarget {
    @Embedded
    public TargetEntity target;
    @Relation(
            parentColumn = "currency_id",
            entityColumn = "id")
    public CurrencyEntity currency;
    @Relation(
            parentColumn = "category_id",
            entityColumn = "id")
    public CategoriesTargetEntity category;
    @Relation(
            parentColumn = "shop_id",
            entityColumn = "id")
    public ShopTargetEntity shop;
    @Relation(
            parentColumn = "user_name_creator_id",
            entityColumn = "id")
    public UserNameEntity creator;
    @Relation(
            parentColumn = "user_name_buyer_id",
            entityColumn = "id")
    public UserNameEntity buyer;

    public TargetEntity getTarget() {
        return target;
    }

    public void setTarget(TargetEntity target) {
        this.target = target;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public CategoriesTargetEntity getCategory() {
        return category;
    }

    public void setCategory(CategoriesTargetEntity category) {
        this.category = category;
    }

    public ShopTargetEntity getShop() {
        return shop;
    }

    public void setShop(ShopTargetEntity shop) {
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

    public void setBuyer(UserNameEntity buyer) {
        this.buyer = buyer;
    }
}
