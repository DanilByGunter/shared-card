package com.project.shared_card.retrofit.model;

import java.time.LocalDateTime;

public class Target {
    private long id_product;
    private long id_category;
    private long id_shop;
    private long id_user;
    private long id_buyer;
    private String product_name;
    private float price;
    private boolean status;
    private LocalDateTime date_first;
    private LocalDateTime date_buy;

    public Target() {
    }

    public Target(long id_product, long id_category, long id_shop, long id_user, long id_buyer, String product_name, float price, boolean status, LocalDateTime date_first, LocalDateTime date_buy) {
        this.id_product = id_product;
        this.id_category = id_category;
        this.id_shop = id_shop;
        this.id_user = id_user;
        this.id_buyer = id_buyer;
        this.product_name = product_name;
        this.price = price;
        this.status = status;
        this.date_first = date_first;
        this.date_buy = date_buy;
    }

    public long getId_product() {
        return id_product;
    }

    public void setId_product(long id_product) {
        this.id_product = id_product;
    }

    public long getId_category() {
        return id_category;
    }

    public void setId_category(long id_category) {
        this.id_category = id_category;
    }

    public long getId_shop() {
        return id_shop;
    }

    public void setId_shop(long id_shop) {
        this.id_shop = id_shop;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public long getId_buyer() {
        return id_buyer;
    }

    public void setId_buyer(long id_buyer) {
        this.id_buyer = id_buyer;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getDate_first() {
        return date_first;
    }

    public void setDate_first(LocalDateTime date_first) {
        this.date_first = date_first;
    }

    public LocalDateTime getDate_buy() {
        return date_buy;
    }

    public void setDate_buy(LocalDateTime date_buy) {
        this.date_buy = date_buy;
    }

    @Override
    public String toString() {
        return "Target{" +
                "id_product=" + id_product +
                ", id_category=" + id_category +
                ", id_shop=" + id_shop +
                ", id_user=" + id_user +
                ", id_buyer=" + id_buyer +
                ", product_name='" + product_name + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", date_first=" + date_first +
                ", date_buy=" + date_buy +
                '}';
    }
}
