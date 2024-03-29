package com.shopping_app.service;

import com.shopping_app.entity.Product;
import com.shopping_app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        // You can implement additional validation logic here if needed
        return productRepository.save(product);
    }

    public int getTotalOrdered() {
        // Call the method from ProductRepository to get the total ordered quantity
        Integer totalOrdered = productRepository.getTotalOrdered();
        return totalOrdered != null ? totalOrdered.intValue() : 0;
    }

    public int getProductPrice() {
        // Call the method from ProductRepository to get the product price
        return productRepository.getProductPrice();
    }

    public int getAvailableQuantity() {
        // Call the method from ProductRepository to get the available quantity
        return productRepository.getAvailableQuantity();
    }
}