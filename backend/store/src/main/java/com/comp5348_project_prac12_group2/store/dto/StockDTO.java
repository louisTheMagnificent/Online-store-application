package com.comp5348_project_prac12_group2.store.dto;

import lombok.*;

@Getter

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    private String productName; // Product name
    private String sellerName;     // Customer Name
    private Long productId;      // Product ID
    private Integer quantity;     // Aggregated quantity
}
