package com.project.shared_card.activity.main_screen.check.tabs.target.model;

import java.time.LocalDateTime;

public class Target {
    String name;
    String category;
    int price;
    LocalDateTime date;
    String nameCreator;
    String currency;
    Boolean status;

    public Target(String name, String category, int price, LocalDateTime date, String nameCreator, String currency, Boolean status) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.date = date;
        this.nameCreator = nameCreator;
        this.currency = currency;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int sell) {
        this.price = price;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}