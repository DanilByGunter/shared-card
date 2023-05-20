package com.project.shared_card.database.entity.statistic.model;

public class Stats {
    private String name;
    private Integer count;

    public Stats() {
    }

    public Stats(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
