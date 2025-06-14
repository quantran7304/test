package com.javaweb.service;

import java.util.Map;

public interface DiscountService {
    Map<String, Object> applyDiscountCode(Integer userId, String discountCode, Double totalAmount, Integer listingId);
}