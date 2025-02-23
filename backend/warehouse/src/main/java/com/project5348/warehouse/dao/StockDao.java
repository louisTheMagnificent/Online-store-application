package com.project5348.warehouse.dao;

public class StockDao {

    private String productName;
    private Integer aggregatedQuantity;
    private final Long customerId;

    private final Long productId;

    // Constructor that matches the query result
    public StockDao(String productName, Long aggregatedQuantity, Long customerId, Long productId) {
        this.productName = productName;
        this.aggregatedQuantity = aggregatedQuantity.intValue();  // Converting Long to Integer
        this.customerId = customerId;
        this.productId = productId;
    }

    // Getters and setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getAggregatedQuantity() {
        return aggregatedQuantity;
    }

    public void setAggregatedQuantity(Integer aggregatedQuantity) {
        this.aggregatedQuantity = aggregatedQuantity;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }
}
