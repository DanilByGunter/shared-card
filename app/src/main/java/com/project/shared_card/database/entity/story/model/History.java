package com.project.shared_card.database.entity.story.model;

import androidx.room.ColumnInfo;

import com.project.shared_card.activity.converter.DateConverter;

import java.time.LocalDateTime;

public class History {
    String currency;
    String product;
    String category;
    String shop;
    String buyer;
    @ColumnInfo(name = "date_first")
    long dataFirst;
    @ColumnInfo(name = "date_last")
    long dataLast;
    int price;
    String count;
    String metric;

    public History(String product, String category, String shop, String buyer, long dataFirst, long dataLast, int price, String currency, String count, String metric) {
        this.product = product;
        this.category = category;
        this.shop = shop;
        this.buyer = buyer;
        this.dataFirst = dataFirst;
        this.dataLast = dataLast;
        this.price = price;
        this.currency = currency;
        this.count=count;
        this.metric = metric;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public long getDataFirst() {
        return dataFirst;
    }

    public void setDataFirst(long dataFirst) {
        this.dataFirst = dataFirst;
    }

    public LocalDateTime getDataLast() {
        return DateConverter.FromLongDateToLocalDateTime(dataLast);
    }

    public void setDataLast(long dataLast) {
        this.dataLast = dataLast;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }
}
