package com.javaweb.service.impl;

import com.javaweb.repository.DiscountCodeRepository;
import com.javaweb.repository.ListingRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.repository.PackageMemberRepository;
import com.javaweb.repository.entity.DiscountCodeEntity;
import com.javaweb.repository.entity.ListingEntity;
import com.javaweb.repository.entity.UserEntity;
import com.javaweb.repository.entity.PackageMemberEntity;
import com.javaweb.service.DiscountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountServiceImpl.class);

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PackageMemberRepository packageMemberRepository;

    @Override
    public Map<String, Object> applyDiscountCode(Integer userId, String discountCode, Double totalAmount, Integer listingId) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (discountCode == null || totalAmount == null || totalAmount <= 0) {
                logger.error("Invalid input parameters: userId={}, discountCode={}, totalAmount={}", userId, discountCode, totalAmount);
                result.put("success", false);
                result.put("message", "Invalid input parameters");
                return result;
            }

            Optional<UserEntity> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                logger.error("UserID {} not found", userId);
                result.put("success", false);
                result.put("message", "User not found");
                return result;
            }

            Optional<PackageMemberEntity> packageMemberOpt = packageMemberRepository.findByUserIdAndStatus(userId, "Active");
            if (packageMemberOpt.isEmpty() || packageMemberOpt.get().getEndDate().isBefore(LocalDate.now())) {
                logger.error("UserID {} is not an active member", userId);
                result.put("success", false);
                result.put("message", "Only active members can apply discount codes");
                return result;
            }

            Optional<DiscountCodeEntity> codeOpt = discountCodeRepository.findByCode(discountCode);
            if (codeOpt.isEmpty()) {
                logger.error("Discount code {} not found", discountCode);
                result.put("success", false);
                result.put("message", "Invalid discount code");
                return result;
            }
            DiscountCodeEntity code = codeOpt.get();

            if (!code.getIsActive()) {
                logger.error("Discount code {} is not active", discountCode);
                result.put("success", false);
                result.put("message", "Invalid discount code");
                return result;
            }

            if (code.getExpirationDate() != null && code.getExpirationDate().isBefore(LocalDateTime.now())) {
                logger.error("Discount code {} has expired", discountCode);
                result.put("success", false);
                result.put("message", "This code has expired");
                return result;
            }

            if (code.getUsageLimit() != null && code.getUsedCount() >= code.getUsageLimit()) {
                logger.error("Discount code {} has reached usage limit", discountCode);
                result.put("success", false);
                result.put("message", "This code is no longer available");
                return result;
            }

            Optional<ListingEntity> listingOpt = Optional.empty();
            if (listingId != null) {
                listingOpt = listingRepository.findById(listingId);
                if (listingOpt.isEmpty()) {
                    logger.error("ListingID {} not found", listingId);
                    result.put("success", false);
                    result.put("message", "Invalid listing ID");
                    return result;
                }
            }

            double discountAmount = 0.0;
            if ("percentage".equals(code.getDiscountType())) {
                discountAmount = totalAmount * (code.getValue() / 100);
            } else if ("fixed".equals(code.getDiscountType())) {
                discountAmount = Math.min(code.getValue(), totalAmount);
            }
            double newTotal = totalAmount - discountAmount;

            code.setUsedCount(code.getUsedCount() + 1);
            discountCodeRepository.save(code);

            if (listingOpt.isPresent()) {
                ListingEntity listing = listingOpt.get();
                listing.setPrice(newTotal);
                listingRepository.save(listing);
                logger.info("Updated price for ListingID {} to {}", listingId, newTotal);
            }

            logger.info("Discount applied successfully for userId {}. New total: {}", userId, newTotal);
            result.put("success", true);
            result.put("message", "Discount applied successfully");
            result.put("newTotal", newTotal);
            return result;
        } catch (Exception e) {
            logger.error("Database error: {}", e.getMessage(), e);
            result.put("success", false);
            result.put("message", "Unable to apply discount. Please try again later");
            return result;
        }
    }
}