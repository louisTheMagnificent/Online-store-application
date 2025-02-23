package com.project5348.deliveryco.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order_table") // Ensure the table name does not clash with SQL reserved keywords
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;  // Reference to the customer ID (stored as a simple field)

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;  // Reference to the seller ID (stored as a simple field)

    @Column(name = "product_name", nullable = false)
    private String productName;  // Name of the product being ordered

    @Column(nullable = false)
    private Integer itemQuantities;  // Quantity of the item ordered

    @Column(nullable = false)
    private Double totalPrice;  // Total price of the order

    @Column(nullable = false)
    private LocalDateTime time;  // Timestamp when the order was placed

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;  // Status of the order, includes payment status

    // Constructor for easy object creation
    public Order(Long customerId, Long sellerId, String productName, Integer itemQuantities, Double totalPrice, LocalDateTime time, OrderStatus status) {
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.productName = productName;
        this.itemQuantities = itemQuantities;
        this.totalPrice = totalPrice;
        this.time = time;
        this.status = status;
    }

    public enum OrderStatus {
        PAID,               // Payment successful, ready to be processed for delivery
        READY_FOR_PICKUP,    // The order is ready to be picked up by DeliveryCo
        PICKED_UP,           // DeliveryCo has picked up the goods from the warehouse
        IN_DEPOT,            // Goods have arrived at the DeliveryCo depot
        ON_DELIVERY_TRUCK,   // Goods are on the delivery truck and on their way to the customer
        DELIVERED,           // Goods have been delivered to the customer
        CANCELLED            // Order cancelled, payment refunded
    }
}
