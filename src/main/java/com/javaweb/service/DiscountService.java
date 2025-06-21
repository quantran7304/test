package com.javaweb.service;

import com.javaweb.model.DiscountApplyResponse;
import com.javaweb.model.DiscountUsageResponse;

public interface DiscountService {
    DiscountApplyResponse applyDiscount(Integer userId, Integer listingId, String discountCode);
    DiscountUsageResponse getUsedDiscountCodes(Integer userId);
}