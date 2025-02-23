package com.project5348.warehouse.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;  // Many-to-one with Product

    @ManyToOne
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;  // Many-to-one with Storage

    @Column(nullable = false)
    private Integer quantity;  // Quantity of the product in this storage

    public Stock(Product product, Storage storage, Integer quantity) {
        this.product = product;
        this.storage = storage;
        this.quantity = quantity;
    }
}