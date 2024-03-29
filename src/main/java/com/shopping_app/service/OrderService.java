package com.shopping_app.service;

import com.shopping_app.entity.TransactionResult;
import com.shopping_app.entity.Coupon;
import com.shopping_app.entity.PurchaseOrder;
import com.shopping_app.exception.InvalidQuantityException;
import com.shopping_app.exception.OrderNotFoundException;
import com.shopping_app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CouponService couponService;

    public PurchaseOrder placeOrder(Long userId, int quantity, String couponCode) {
        // Check if quantity is valid
        int availableQuantity = productService.getAvailableQuantity();
        if (quantity < 1 || quantity > availableQuantity) {
            throw new InvalidQuantityException("Invalid quantity");
        }

        // Retrieve the coupon from the database
        Coupon coupon = couponService.getCouponByCode(couponCode);

        // Calculate amount after discount
        int price = productService.getProductPrice();
        int discountPercentage = coupon.getDiscountPercentage();
        int totalAmount = quantity * price;
        int discountAmount = totalAmount * discountPercentage / 100;
        int amount = totalAmount - discountAmount;

        // Save order to database
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(userId);
        purchaseOrder.setQuantity(quantity);
        purchaseOrder.setAmount(amount);
        purchaseOrder.setCouponCode(couponCode.trim());
        // Set productId to 1
        purchaseOrder.setStatus("200");
        Random random = new Random();
        int orderId = random.nextInt(1000); // Change 1000 to the maximum range of your order IDs
        purchaseOrder.setOrderId(orderId);

        // Save order and return
        return orderRepository.save(purchaseOrder);
    }


    public TransactionResult makePayment(Long userId, Long orderId, int amount) {
        // Implement logic to simulate payment
        // Placeholder implementation, randomly return success or failure
        Random random = new Random();
        int statusCode = random.nextBoolean() ? 200 : 400;
        String transactionId = "tran" + UUID.randomUUID().toString().replaceAll("-", "");
        String status = statusCode == 200 ? "successful" : "failed";
        return new TransactionResult(userId, orderId, transactionId, status, statusCode);
    }

    public List<PurchaseOrder> getUserOrders(Long userId) {
        // Implement logic to fetch user orders from repository
        return orderRepository.findByUserId(userId);
    }

    public PurchaseOrder getOrder(Long userId, Long orderId) {
        // Implement logic to fetch order by userId and orderId
        Optional<PurchaseOrder> optionalOrder = orderRepository.findByIdAndUserId(orderId, userId);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        } else {
            throw new OrderNotFoundException("Order not found");
        }
    }
}

