package com.project.shared_card.activity.main_screen.check.tabs.current.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Check {
    String name;
    String category;
    int count;
    LocalDateTime date;
    String nameCreator;
    String metric;


    public Check() {
    }

    public Check(String name, String category, int count, LocalDateTime date, String nameCreator,String metric) {
        this.name = name;
        this.category = category;
        this.count = count;
        this.date = date;
        this.nameCreator = nameCreator;
        this.metric = metric;
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
