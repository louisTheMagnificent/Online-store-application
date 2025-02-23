package com.project5348.deliveryco.dao;

import lombok.Getter;

import java.time.LocalDateTime;

public class DetailOrderDao {

    private Long id;

    private Long customerId;

    private String productName;

    private Integer itemQuantities;

    private Double totalPrice;
    private LocalDateTime time;
    private String status;
    private boolean showCancelButton;  // Whether to display the cancel button

    public DetailOrderDao(Long id, Long customerId, String productName, Integer itemQuantities, Double totalPrice, LocalDateTime time, String status, boolean showCancelButton) {
        this.id = id;
        this.customerId = customerId;
        this.productName = productName;
        this.itemQuantities = itemQuantities;
        this.totalPrice = totalPrice;
        this.time = time;
        this.status = status;
        this.showCancelButton = showCancelButton;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getItemQuantities() {
        return itemQuantities;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public boolean isShowCancelButton() {
        return showCancelButton;
    }
}
