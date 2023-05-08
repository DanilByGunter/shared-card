package com.project.shared_card.activity.main_screen.story.model;

import java.time.LocalDateTime;

public class History {
    String currency;
    String product;
    String category;
    String shop;
    String creator;
    String buyer;
    LocalDateTime dataFirst;
    LocalDateTime dataLast;
    int price;
    String count;
    String metric;

    public History(String product, String category, String shop, String creator, String buyer, LocalDateTime dataFirst, LocalDateTime dataLast, int price,String currency,String count,String metric) {
        this.product = product;
        this.category = category;
        this.shop = shop;
        this.creator = creator;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public LocalDateTime getDataFirst() {
        return dataFirst;
    }

    public void setDataFirst(LocalDateTime dataFirst) {
        this.dataFirst = dataFirst;
    }

    public LocalDateTime getDataLast() {
        return dataLast;
    }

    public void setDataLast(LocalDateTime dataLast) {
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
