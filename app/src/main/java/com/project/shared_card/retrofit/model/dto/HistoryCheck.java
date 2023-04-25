package com.project.shared_card.retrofit.model.dto;

import java.time.LocalDateTime;

public class HistoryCheck {
    private String product_name;
    private String category_name;
    private float product_count;
    private String metric_name;
    private String shop_name;
    private float price;
    private LocalDateTime date_buy;
    private String user_name;
    private String user_buyer;

    public HistoryCheck() {}

    public HistoryCheck(String product_name, String category_name, float product_count, String metric_name, String shop_name, float price, LocalDateTime date_buy, String user_name, String user_buyer) {
        this.product_name = product_name;
        this.category_name = category_name;
        this.product_count = product_count;
        this.metric_name = metric_name;
        this.shop_name = shop_name;
        this.price = price;
        this.date_buy = date_buy;
        this.user_name = user_name;
        this.user_buyer = user_buyer;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public float getProduct_count() {
        return product_count;
    }

    public void setProduct_count(float product_count) {
        this.product_count = product_count;
    }

    public String getMetric_name() {
        return metric_name;
    }

    public void setMetric_name(String metric_name) {
        this.metric_name = metric_name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getDate_buy() {
        return date_buy;
    }

    public void setDate_buy(LocalDateTime date_buy) {
        this.date_buy = date_buy;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_buyer() {
        return user_buyer;
    }

    public void setUser_buyer(String user_buyer) {
        this.user_buyer = user_buyer;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    @Override
    public String toString() {
        return "HistoryCheck{" +
                "product_name='" + product_name + '\'' +
                ", category_name='" + category_name + '\'' +
                ", product_count=" + product_count +
                ", metric_name='" + metric_name + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", price=" + price +
                ", date_buy=" + date_buy +
                ", user_name='" + user_name + '\'' +
                ", user_buyer='" + user_buyer + '\'' +
                '}';
    }
}
