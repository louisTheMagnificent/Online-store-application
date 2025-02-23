package com.comp5348_project_prac12_group2.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDetailDTO {
    private Long id;            // Product ID (invisible to the user)
    private String name;       // Product name
    private String description; // Product description
    private Double price;      // Product price
    private Integer quantity;   // Quantity available in stock
}
