package com.javaweb.service.impl;

import com.javaweb.repository.ListingRepository;
import com.javaweb.repository.ReportRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.repository.entity.ListingEntity;
import com.javaweb.repository.entity.ReportEntity;
import com.javaweb.repository.entity.UserEntity;
import com.javaweb.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Override
    public Map<String, Object> submitReport(Integer userId, Integer listingId, String reason, String comment) {
        Map<String, Object> result = new HashMap<>();
        try {
            UserEntity user = null;
            if (userId != null) {
                Optional<UserEntity> userOpt = userRepository.findById(userId);
                if (userOpt.isEmpty()) {
                    logger.error("UserID {} not found", userId);
                    result.put("success", false);
                    result.put("message", "Error: User not found");
                    return result;
                }
                user = userOpt.get();
                logger.info("Found User: {}", user.getEmail());
            }

            Optional<ListingEntity> listingOpt = listingRepository.findById(listingId);
            if (listingOpt.isEmpty()) {
                logger.error("ListingID {} not found", listingId);
                result.put("success", false);
                result.put("message", "Error: Listing not found");
                return result;
            }
            ListingEntity listing = listingOpt.get();
            String status = listing.getListingStatus() != null ? listing.getListingStatus() : "NULL";
            logger.info("Found ListingID {} with status: {}", listingId, status);
            if (listing.getListingStatus() != null && !"active".equals(listing.getListingStatus())) {
                logger.error("ListingID {} is not active", listingId);
                result.put("success", false);
                result.put("message", "Error: Listing is not active");
                return result;
            }

            if (user != null && listing.getUserId() != null && listing.getUserId().equals(userId)) {
                logger.error("UserID {} cannot report their own listing", userId);
                result.put("success", false);
                result.put("message", "Error: You cannot report your own listing");
                return result;
            }

            if (user != null && reportRepository.findByUserAndListing(user, listing).isPresent()) {
                logger.error("UserID {} already reported ListingID {}", userId, listingId);
                result.put("success", false);
                result.put("message", "You have already reported this post");
                return result;
            }

            ReportEntity report = new ReportEntity();
            report.setUser(user);
            report.setListing(listing);
            report.setReportDate(LocalDateTime.now());
            report.setReason(reason);
            report.setComment(comment);
            report.setStatus("Pending");
            reportRepository.save(report);
            logger.info("Report submitted for UserID {} and ListingID {}", userId, listingId);
            result.put("success", true);
            result.put("message", "Thank you. Your report has been submitted");
            return result;
        } catch (Exception e) {
            logger.error("Database error: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "Error: Database issue occurred");
            return result;
        }
    }

    @Override
    public String[] getReportReasons() {
        return new String[]{"Spam", "Fraud", "Inappropriate Content", "Other"};
    }
}