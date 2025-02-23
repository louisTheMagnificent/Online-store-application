package com.project5348.warehouse.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long customer;  // Assuming User is another entity

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL)  // One-to-many with Stock
    private List<Stock> stockList;

    public Storage(String name, Long customer, List<Stock> stockList) {
        this.name = name;
        this.customer = customer;
        this.stockList = stockList;
    }
}
