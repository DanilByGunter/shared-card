package com.project.shared_card.retrofit.model.dto;

import java.time.LocalDateTime;

public class ProductTarget {
    private String product_name;
    private String category_name;
    private float price;
    private LocalDateTime date_first;
    private String user_name;

    public ProductTarget() {
    }

    public ProductTarget(String product_name, String category_name, float price, LocalDateTime date_first, String user_name) {
        this.product_name = product_name;
        this.category_name = category_name;
        this.price = price;
        this.date_first = date_first;
        this.user_name = user_name;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getDate_first() {
        return date_first;
    }

    public void setDate_first(LocalDateTime date_first) {
        this.date_first = date_first;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "ProductTarget{" +
                "product_name='" + product_name + '\'' +
                ", category_name='" + category_name + '\'' +
                ", price=" + price +
                ", date_first=" + date_first +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
