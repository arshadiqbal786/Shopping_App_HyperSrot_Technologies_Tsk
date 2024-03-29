package com.shopping_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private int orderId;

    private int quantity;

    private int amount;

    private String couponCode;

    private String transactionId;

    private String status;

    // Constructors, getters, and setters
}


