package com.project5348.deliveryco.dao;

public class OrderDao {
    private String productName;
    private Integer itemQuantity;
    private Long userId;
    private Long id;

    public OrderDao(String productName, Integer itemQuantity, Long userId, Long id) {
        this.productName = productName;
        this.itemQuantity = itemQuantity;
        this.userId = userId;
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }
}
