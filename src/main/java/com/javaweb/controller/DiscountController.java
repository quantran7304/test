package com.javaweb.controller;

import com.javaweb.model.DiscountApplyRequest;
import com.javaweb.model.DiscountApplyResponse;
import com.javaweb.model.DiscountUsageResponse;
import com.javaweb.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/discounts")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    // API POST để áp dụng mã giảm giá
    @PostMapping("/apply")
    public ResponseEntity<DiscountApplyResponse> applyDiscount(@RequestBody(required = false) DiscountApplyRequest request) {
        DiscountApplyResponse response = discountService.applyDiscount(
                request.getUserId(),
                request.getListingId(),
                request.getDiscountCode()
        );
        return ResponseEntity.ok(response);
    }

    // API POST để xem danh sách mã giảm giá đã dùng
    @PostMapping("/usage")
    public ResponseEntity<DiscountUsageResponse> getUsedDiscountCodes(@RequestBody(required = false) DiscountApplyRequest request) {
        DiscountUsageResponse response = discountService.getUsedDiscountCodes(request.getUserId());
        if (response.getMessage().equals("User does not exist.")) {
            return ResponseEntity.status(404).body(response);
        }
        return ResponseEntity.ok(response);
    }
}