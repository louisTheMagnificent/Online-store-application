package com.project5348.warehouse.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String productName;

    @Column
    private String description;

    @Column(nullable = false)
    private Double price;

    public Product(String productName, String description, Double price) {
        this.productName = productName;
        this.description = description;
        this.price = price;
    }
}
