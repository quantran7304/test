package com.javaweb.service.impl;

import com.javaweb.model.ReportDTO;
import com.javaweb.model.ReportResponseDTO;
import com.javaweb.repository.ListingRepository;
import com.javaweb.repository.ReportRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.repository.entity.ListingEntity;
import com.javaweb.repository.entity.ReportEntity;
import com.javaweb.repository.entity.UserEntity;
import com.javaweb.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository, ListingRepository listingRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    private static final List<String> VALID_REASONS = List.of("Fraudulent content", "Inappropriate content", "Spam", "Other");

    private boolean isValidReportAction(ReportDTO request) {
        return request != null && request.getUserId() != null && request.getListingId() != null &&
                userRepository.findById(request.getUserId()).isPresent() &&
                listingRepository.findById(request.getListingId()).isPresent() &&
                (listingRepository.findById(request.getListingId()).flatMap(listing -> Optional.ofNullable(listing.getUserId()))
                        .map(userId -> !userId.equals(request.getUserId())).orElse(true));
    }

    @Override
    public ReportResponseDTO createReport(ReportDTO request) {
        if (request == null) {
            return new ReportResponseDTO(null, false, "Request body is missing.");
        }
        if (!isValidReportAction(request)) {
            return new ReportResponseDTO(null, false, "Invalid input or listing does not exist.");
        }
        String reason = request.getReason();
        String details = request.getDetails() != null ? request.getDetails() : "";
        if (reason == null || !VALID_REASONS.contains(reason)) {
            return new ReportResponseDTO(null, false, "Invalid reason.");
        }

        Optional<ListingEntity> listingOpt = listingRepository.findById(request.getListingId());
        if (listingOpt.isPresent() && listingOpt.get().getUserId() != null && listingOpt.get().getUserId().equals(request.getUserId())) {
            return new ReportResponseDTO(null, false, "You cannot report your own listing.");
        }

        Optional<ReportEntity> existingReport = reportRepository.findByUserIdAndListingId(
                request.getUserId(), request.getListingId());
        if (existingReport.isPresent()) {
            return new ReportResponseDTO(null, false, "You have already reported this post.");
        }

        ReportEntity report = new ReportEntity(request.getUserId(), request.getListingId(), reason, details, LocalDateTime.now(), "pending");
        try {
            ReportEntity savedReport = reportRepository.save(report);
            if (savedReport == null || savedReport.getReportId() == null) {
                logger.error("Failed to save report for userId: {}, listingId: {}", request.getUserId(), request.getListingId());
                return new ReportResponseDTO(null, false, "Failed to save report.");
            }
            logger.info("Report saved successfully for userId: {}, listingId: {}", request.getUserId(), request.getListingId());
            return new ReportResponseDTO(null, true, "Thank you. Your report has been submitted.");
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation saving report for userId: {}, listingId: {} - {}", request.getUserId(), request.getListingId(), e.getMessage());
            return new ReportResponseDTO(null, false, "Data integrity error. Please check user or listing ID.");
        } catch (Exception e) {
            logger.error("Error saving report for userId: {}, listingId: {} - {}", request.getUserId(), request.getListingId(), e.getMessage());
            return new ReportResponseDTO(null, false, "Unable to send report. Please try again later.");
        }
    }

    @Override
    public ReportResponseDTO deleteReport(ReportDTO request) {
        if (request == null) {
            return new ReportResponseDTO(null, false, "Request body is missing.");
        }
        if (!isValidReportAction(request)) {
            return new ReportResponseDTO(null, false, "Invalid input or listing does not exist.");
        }

        Optional<ReportEntity> reportOpt = reportRepository.findByUserIdAndListingId(
                request.getUserId(), request.getListingId());
        if (reportOpt.isEmpty()) {
            return new ReportResponseDTO(null, false, "Report does not exist.");
        }

        try {
            reportRepository.delete(reportOpt.get());
            logger.info("Report deleted successfully for userId: {}, listingId: {}", request.getUserId(), request.getListingId());
            return new ReportResponseDTO(null, true, "Report has been deleted.");
        } catch (Exception e) {
            logger.error("Error deleting report for userId: {}, listingId: {} - {}", request.getUserId(), request.getListingId(), e.getMessage());
            return new ReportResponseDTO(null, false, "Unable to delete report. Please try again later.");
        }
    }

    @Override
    public ReportResponseDTO getReports(Integer userId) {
        if (userId == null) {
            return new ReportResponseDTO(null, false, "User ID is required.");
        }
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return new ReportResponseDTO(null, false, "User does not exist.");
        }

        List<ReportEntity> reports = reportRepository.findByUserId(userId)
                .stream()
                .filter(report -> report != null && report.getListingId() != null)
                .toList();
        if (reports.isEmpty()) {
            return new ReportResponseDTO(Collections.emptyList(), true, "No reports found.");
        }

        List<ReportDTO> reportDTOs = reports.stream().map(report -> {
            Integer listingId = report.getListingId();
            String sellerName = null;
            Optional<ListingEntity> listingOpt = listingRepository.findById(listingId);
            if (listingOpt.isPresent()) {
                Integer listingUserId = listingOpt.get().getUserId();
                if (listingUserId != null) {
                    Optional<UserEntity> sellerOpt = userRepository.findById(listingUserId);
                    sellerName = sellerOpt.map(user -> user.getFirstName() + " " + user.getLastName()).orElse("Unknown Seller");
                }
            }
            return new ReportDTO(
                    report.getReportId(),
                    report.getUserId(),
                    listingId,
                    report.getReason(),
                    report.getDetails(),
                    report.getReportDate(),
                    report.getStatus(),
                    sellerName
            );
        }).filter(Objects::nonNull).toList();

        return new ReportResponseDTO(reportDTOs, true, "Reports retrieved successfully.");
    }
}