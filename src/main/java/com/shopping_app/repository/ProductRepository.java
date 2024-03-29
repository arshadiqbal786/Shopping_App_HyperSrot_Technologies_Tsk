package com.shopping_app.repository;

import com.shopping_app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Add custom query methods if needed
    @Query("SELECT SUM(o.quantity) FROM PurchaseOrder o")
    Integer getTotalOrdered();

    @Query("SELECT p.price FROM Product p")
    Integer getProductPrice();

    @Query("SELECT p.availableQuantity FROM Product p")
    Integer getAvailableQuantity();
}
