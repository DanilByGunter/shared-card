package com.project.shared_card.database.entity.statistic.model;

public class Stats {
    private String name;
    private Integer count;
    private Integer summa;

    public Stats() {
    }

    public Stats(String name, Integer count, Integer summa) {
        this.name = name;
        this.count = count;
        this.summa = summa;
    }

    public Integer getSumma() {
        return summa;
    }

    public void setSumma(Integer summa) {
        this.summa = summa;
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
