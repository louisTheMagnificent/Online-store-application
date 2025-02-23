package com.comp5348_project_prac12_group2.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderDetailDTO {
    private Long id;
    private String customerName;
    private String productName;
    private Integer itemQuantities;
    private Double totalPrice;
    private String time;
    private String status;
    private boolean showCancelButton;  // Whether to display the cancel button
}
