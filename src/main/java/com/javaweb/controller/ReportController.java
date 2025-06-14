package com.javaweb.controller;

import com.javaweb.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/form")
    public ResponseEntity<Map<String, Object>> getReportForm() {
        Map<String, Object> response = new HashMap<>();
        response.put("reasons", reportService.getReportReasons());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> submitReport(
            @RequestParam(required = false) Integer userId,
            @RequestParam Integer listingId,
            @RequestParam String reason,
            @RequestParam String comment) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (listingId == null || reason == null || comment == null) {
                response.put("success", false);
                response.put("message", "Listing ID, reason, and comment are required");
                return ResponseEntity.badRequest().body(response);
            }
            Map<String, Object> result = reportService.submitReport(userId, listingId, reason, comment);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error submitting report: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}