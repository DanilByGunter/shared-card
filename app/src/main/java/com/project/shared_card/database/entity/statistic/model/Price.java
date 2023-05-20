package com.project.shared_card.database.entity.statistic.model;

public class Price {
    private Integer price;
    private Long date;

    public Price() {
    }

    public Price(Integer price, Long date) {
        this.price = price;
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
