package com.shopping_app.controller;

import com.shopping_app.entity.TransactionResult;
import com.shopping_app.entity.PurchaseOrder;
import com.shopping_app.exception.InvalidCouponException;
import com.shopping_app.exception.InvalidQuantityException;
import com.shopping_app.exception.OrderNotFoundException;
import com.shopping_app.repository.OrderRepository;
import com.shopping_app.repository.TransactionResultRepository;
import com.shopping_app.service.CouponService;
import com.shopping_app.service.OrderService;
import com.shopping_app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ShoppingController {

    private final ProductService productService;
    private final CouponService couponService;
    private final OrderService orderService;
    private final TransactionResultRepository transactionResultRepository;
    private final OrderRepository orderRepository; // Assuming you have an OrderRepository


    private final Set<Long> processedOrderIds = new HashSet<>();

    @Autowired
    public ShoppingController(ProductService productService, CouponService couponService, OrderService orderService, TransactionResultRepository transactionResultRepository ,OrderRepository orderRepository) {
        this.productService = productService;
        this.couponService = couponService;
        this.orderService = orderService;
        this.transactionResultRepository = transactionResultRepository;
        this.orderRepository = orderRepository;
    }

    // GET /inventory
    @GetMapping("/inventory")
    public ResponseEntity<Map<String, Object>> getInventory() {
        Map<String, Object> inventory = new HashMap<>();
        Integer totalOrdered = productService.getTotalOrdered();
        int ordered = totalOrdered != null ? totalOrdered.intValue() : 0;
        inventory.put("ordered", ordered);
        inventory.put("ordered", productService.getTotalOrdered());
        inventory.put("price", productService.getProductPrice());
        inventory.put("available", productService.getAvailableQuantity());
        return ResponseEntity.ok(inventory);
    }

    // GET /fetchCoupons
    @GetMapping("/fetchCoupons")
    public ResponseEntity<Map<String, Integer>> fetchCoupons() {
        Map<String, Integer> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }

    // POST /{userId}/order
    @PostMapping("/{userId}/order")
    public ResponseEntity<?> placeOrder(@PathVariable Long userId,
                                        @RequestParam int quantity,
                                        @RequestParam String coupon) {
        try {
            PurchaseOrder purchaseOrder = orderService.placeOrder(userId, quantity, coupon);
            return ResponseEntity.ok(purchaseOrder);
        } catch (InvalidQuantityException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("description", e.getMessage()));
        } catch (InvalidCouponException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("description", e.getMessage()));
        }
    }




    // POST /{userId}/{orderId}/pay
    @PostMapping("/{userId}/{orderId}/pay")
    public ResponseEntity<?> makePayment(@PathVariable Long userId,
                                         @PathVariable Long orderId,
                                         @RequestParam Integer amount) {
        // Check if payment for this orderId has already been processed
        if (processedOrderIds.contains(orderId)) {
            String description = "Payment for this order has already been processed";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("description", description));
        }

        // Implement logic to simulate payment
        Random random = new Random();
        int statusCode = random.nextBoolean() ? 200 : 400;
        String transactionId = "tran" + UUID.randomUUID().toString().replaceAll("-", "");
        String status = statusCode == 200 ? "successful" : "failed";

        // Create a new TransactionResult object
        TransactionResult transactionResult = new TransactionResult(userId, orderId, transactionId, status, statusCode);

        // Save the transaction result to the database
        transactionResultRepository.save(transactionResult);

        // If payment is successful, add orderId to processedOrderIds set
        if (status.equals("successful")) {
            processedOrderIds.add(orderId);
        }

        // Return the transaction result
        return ResponseEntity.ok(transactionResult);
    }

    // GET /{userId}/orders
    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<PurchaseOrder>> getUserOrders(@PathVariable Long userId) {
        List<PurchaseOrder> purchaseOrders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(purchaseOrders);
    }

    // GET /{userId}/orders/{orderId}
    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long userId,
                                             @PathVariable int orderId) {
        Optional<PurchaseOrder> optionalOrder = orderRepository.findByOrderId(orderId);
        if (optionalOrder.isPresent()) {
            PurchaseOrder order = optionalOrder.get();
            if (order.getUserId() == userId) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("description", "Order does not belong to this user"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("description", "Order not found"));
        }
    }
}
