package com.comp5348_project_prac12_group2.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDTO {
    private String productName;
    private Integer itemQuantity;
    private String username;
    private Long id;
}
