package com.shopping_app.service;

import com.shopping_app.entity.Coupon;
import com.shopping_app.exception.InvalidCouponException;
import com.shopping_app.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;


    public Map<String, Integer> getAllCoupons() {
        // Fetch all coupons from the database
        List<Coupon> allCoupons = couponRepository.findAll();

        // Convert the list of coupons to a map of coupon codes and their discount percentages
        Map<String, Integer> coupons = allCoupons.stream()
                .collect(Collectors.toMap(Coupon::getCode, Coupon::getDiscountPercentage));

        return coupons;
    }
    public Coupon getCouponByCode(String code) {
        // Trim whitespace and newline characters from the coupon code
        code = code.trim();

        Optional<Coupon> optionalCoupon = couponRepository.findByCodeIgnoreCase(code);
        if (optionalCoupon.isPresent()) {
            return optionalCoupon.get();
        } else {
            throw new InvalidCouponException("Invalid coupon code: " + code);
        }
    }




    public Coupon createCoupon(Coupon coupon) {
        // Save the new coupon to the database
        return couponRepository.save(coupon);
    }
}