package com.project.shared_card.retrofit.model.dto;

import java.time.LocalDateTime;

public class ProductCheck {
    private String product_name;
    private String category_name;
    private float product_count;
    private String metric_name;
    private LocalDateTime date_first;
    private String user_name;

    public ProductCheck() {
    }

    public ProductCheck(String product_name, String category_name, float product_count, String metric_name, LocalDateTime date_first, String user_name) {
        this.product_name = product_name;
        this.category_name = category_name;
        this.product_count = product_count;
        this.metric_name = metric_name;
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
        return "Product{" +
                ", product_name='" + product_name + '\'' +
                ", category_name=" + category_name +
                ", product_count=" + product_count +
                ", metric_name='" + metric_name + '\'' +
                ", date_first=" + date_first +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
