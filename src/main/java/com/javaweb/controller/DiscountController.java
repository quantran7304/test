package com.javaweb.controller;

import com.javaweb.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/discount")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> applyDiscountCode(
            @RequestParam Integer userId,
            @RequestParam String discountCode,
            @RequestParam Double totalAmount,
            @RequestParam(required = false) Integer listingId) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (discountCode == null || totalAmount == null) {
                response.put("success", false);
                response.put("message", "Discount code and total amount are required");
                return ResponseEntity.badRequest().body(response);
            }
            Map<String, Object> result = discountService.applyDiscountCode(userId, discountCode, totalAmount, listingId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error applying discount: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}