package com.javaweb.model;

import java.time.LocalDateTime;

public class ReportDTO {
    private Integer reportId;
    private Integer userId;
    private Integer listingId;
    private String reason;
    private String details;
    private LocalDateTime reportDate;
    private String status;
    private String sellerName;

    // Constructor mặc định (cho @RequestBody(required = false))
    public ReportDTO() {}

    // Constructor cho input (dùng trong controller)
    public ReportDTO(Integer userId, Integer listingId, String reason, String details) {
        this.userId = userId;
        this.listingId = listingId;
        this.reason = reason;
        this.details = details;
    }

    // Constructor cho output (dùng trong service)
    public ReportDTO(Integer reportId, Integer userId, Integer listingId, String reason, String details, LocalDateTime reportDate, String status, String sellerName) {
        this.reportId = reportId;
        this.userId = userId;
        this.listingId = listingId;
        this.reason = reason;
        this.details = details;
        this.reportDate = reportDate;
        this.status = status;
        this.sellerName = sellerName;
    }

    // Getters and Setters
    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getListingId() {
        return listingId;
    }

    public void setListingId(Integer listingId) {
        this.listingId = listingId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSellerName() {
        return sellerName;
    }

    public ReportDTO setSellerName(String sellerName) {
        this.sellerName = sellerName;
        return this;
    }
}