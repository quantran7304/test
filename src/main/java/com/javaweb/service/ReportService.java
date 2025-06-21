package com.javaweb.service;

import com.javaweb.model.ReportDTO;
import com.javaweb.model.ReportResponseDTO;

public interface ReportService {
    ReportResponseDTO createReport(ReportDTO request);
    ReportResponseDTO deleteReport(ReportDTO request);
    ReportResponseDTO getReports(Integer userId);
}