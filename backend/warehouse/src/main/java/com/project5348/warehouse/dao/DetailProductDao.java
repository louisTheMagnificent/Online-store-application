package com.project5348.warehouse.dao;

public class DetailProductDao {

    private Long id;            // Product ID (invisible to the user)
    private String name;        // Product name
    private String description; // Product description
    private Double price;       // Product price
    private Integer quantity;   // Aggregated quantity available in stock

    // Constructor
    public DetailProductDao(Long id, String name, String description, Double price, Long aggregatedQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = aggregatedQuantity.intValue();  // Converting Long to Integer
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
