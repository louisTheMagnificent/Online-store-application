package com.project5348.bank.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Long customerId;  // Reference to Customer in the store database

    @Column(nullable = false)
    private Double balance;

    // Constructor
    public Account(Long customerId, Double balance) {
        this.customerId = customerId;
        this.balance = balance;
    }
}
