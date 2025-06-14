package com.javaweb.service;

import java.util.Map;

public interface ReportService {
    Map<String, Object> submitReport(Integer userId, Integer listingId, String reason, String comment);
    String[] getReportReasons();
}