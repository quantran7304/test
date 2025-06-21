package com.javaweb.controller;

import com.javaweb.model.ReportDTO;
import com.javaweb.model.ReportResponseDTO;
import com.javaweb.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // API POST để tạo báo cáo mới
    @PostMapping("/create")
    public ResponseEntity<ReportResponseDTO> createReport(@RequestBody(required = false) ReportDTO request) {
        ReportResponseDTO response = reportService.createReport(request);
        return ResponseEntity.ok(response);
    }

    // API POST để xóa báo cáo
    @PostMapping("/delete")
    public ResponseEntity<ReportResponseDTO> deleteReport(@RequestBody(required = false) ReportDTO request) {
        ReportResponseDTO response = reportService.deleteReport(request);
        return ResponseEntity.ok(response);
    }

    // API GET để lấy danh sách báo cáo
    @GetMapping("/list")
    public ResponseEntity<ReportResponseDTO> getReports(@RequestParam(required = false) Integer userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().body(new ReportResponseDTO(null, false, "User ID is required."));
        }
        ReportResponseDTO response = reportService.getReports(userId);
        if ("User does not exist.".equals(response.getMessage())) {
            return ResponseEntity.status(404).body(response);
        }
        return ResponseEntity.ok(response);
    }
}