package com.project5348.warehouse.dao;

public class ProductQuantityAndPriceDao {
    private Integer amount;
    private Double price;

    public ProductQuantityAndPriceDao(Integer amount, Double price) {
        this.amount = amount;
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public Double getPrice() {
        return price;
    }
}
