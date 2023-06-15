package com.project.shared_card.activity.main_screen.check.tabs.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cell {
    String name;
    String category;
    int countOrPrice;
    LocalDateTime date;
    String nameCreator;
    String metricOrCurrency;
    int status;

    public Cell(String name, String category, int countOrPrice, LocalDateTime date, String nameCreator, String metricOrCurrency, int status) {
        this.name = name;
        this.category = category;
        this.countOrPrice = countOrPrice;
        this.date = date;
        this.nameCreator = nameCreator;
        this.metricOrCurrency = metricOrCurrency;
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

    public int getCountOrPrice() {
        return countOrPrice;
    }

    public void setCountOrPrice(int countOrPrice) {
        this.countOrPrice = countOrPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public String getDateString() {
        return date.format(DateTimeFormatter.ofPattern("dd.MM HH:mm"));
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

    public String getMetricOrCurrency() {
        return metricOrCurrency;
    }

    public void setMetricOrCurrency(String metricOrCurrency) {
        this.metricOrCurrency = metricOrCurrency;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
