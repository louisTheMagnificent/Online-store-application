package com.comp5348_project_prac12_group2.store.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId; // This can be the ID of the customer

    // Constructor
    public UserDTO(Long userId) {
        this.userId = userId;
    }
}
