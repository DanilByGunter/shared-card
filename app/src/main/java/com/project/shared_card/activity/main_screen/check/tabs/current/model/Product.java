package com.project.shared_card.activity.main_screen.check.tabs.current.model;

import com.project.shared_card.activity.database.entity.check.product.ProductEntity;

import java.time.LocalDateTime;

public class Product {
    ProductEntity entity;
    String name;
    String category;
    int count;
    LocalDateTime date;
    String nameCreator;
    String metric;
    int status;


    public Product() {
    }

    public Product(String name, String category, int count, LocalDateTime date, String nameCreator, String metric,int status,ProductEntity entity) {
        this.name = name;
        this.category = category;
        this.count = count;
        this.date = date;
        this.nameCreator = nameCreator;
        this.metric = metric;
        this.status = status;
        this.entity =entity;
    }

    public ProductEntity getEntity() {
        return entity;
    }

    public void setEntity(ProductEntity entity) {
        this.entity = entity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNameCreator() {
        return nameCreator;
    }

    public void setNameCreator(String nameCreator) {
        this.nameCreator = nameCreator;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }
}
