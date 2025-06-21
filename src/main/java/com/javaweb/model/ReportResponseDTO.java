package com.javaweb.model;

import java.util.List;

public class ReportResponseDTO {
    private List<ReportDTO> reports; // Đổi từ 'data' thành 'reports' để khớp với response mong đợi
    private boolean success;
    private String message;

    // Constructors
    public ReportResponseDTO() {}

    public ReportResponseDTO(List<ReportDTO> reports, boolean success, String message) {
        this.reports = reports;
        this.success = success;
        this.message = message;
    }

    // Getters and Setters
    public List<ReportDTO> getReports() {
        return reports;
    }

    public void setReports(List<ReportDTO> reports) {
        this.reports = reports;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}