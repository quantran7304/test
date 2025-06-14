package com.javaweb.controller;

import com.javaweb.model.PaymentDTO;
import com.javaweb.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowedHeaders = "*", methods = {RequestMethod.GET})
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<PaymentDTO>> getTransactionHistory(@PathVariable Integer userId) {
        List<PaymentDTO> transactions = paymentService.getTransactionHistory(userId);
        return ResponseEntity.ok(transactions);
    }
}