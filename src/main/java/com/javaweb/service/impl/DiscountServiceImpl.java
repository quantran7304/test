package com.javaweb.service.impl;

import com.javaweb.model.DiscountApplyResponse;
import com.javaweb.model.DiscountUsageResponse;
import com.javaweb.repository.DiscountCodeRepository;
import com.javaweb.repository.DiscountUsageRepository;
import com.javaweb.repository.ListingRepository;
import com.javaweb.repository.PropertyRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.repository.entity.DiscountCodeEntity;
import com.javaweb.repository.entity.DiscountUsageEntity;
import com.javaweb.repository.entity.ListingEntity;
import com.javaweb.repository.entity.PropertyEntity;
import com.javaweb.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    @Autowired
    private DiscountUsageRepository discountUsageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public DiscountApplyResponse applyDiscount(Integer userId, Integer listingId, String discountCode) {
        if (userId == null || listingId == null || discountCode == null) {
            return new DiscountApplyResponse(null, "Invalid input parameters.");
        }

        // Kiểm tra user tồn tại
        if (userRepository.findById(userId).isEmpty()) {
            return new DiscountApplyResponse(null, "User does not exist.");
        }

        // Kiểm tra listing tồn tại và lấy PropertyID
        Optional<ListingEntity> listingOpt = listingRepository.findById(listingId);
        if (listingOpt.isEmpty()) {
            return new DiscountApplyResponse(null, "Listing does not exist.");
        }
        ListingEntity listing = listingOpt.get();
        Optional<PropertyEntity> propertyOpt = propertyRepository.findById(listing.getPropertyId());
        if (propertyOpt.isEmpty()) {
            return new DiscountApplyResponse(null, "Property does not exist.");
        }
        PropertyEntity property = propertyOpt.get();
        Double originalAmount = property.getPrice();
        if (originalAmount == null) {
            return new DiscountApplyResponse(null, "Property price is not available.");
        }

        // Kiểm tra user không áp mã cho bài của chính mình
        if (listing.getUserId() != null && listing.getUserId().equals(userId)) {
            return new DiscountApplyResponse(originalAmount, "You cannot apply discount to your own listing.");
        }

        // Kiểm tra xem user đã sử dụng mã này trước đó chưa
        boolean hasUsedCode = discountUsageRepository.findByUserIdAndDiscountCodeId(userId,
                        discountCodeRepository.findByCode(discountCode).map(DiscountCodeEntity::getDiscountCodeId).orElse(null))
                .stream().findAny().isPresent();
        if (hasUsedCode) {
            // Lấy lại property từ database để lấy giá đã cập nhật
            Optional<PropertyEntity> updatedPropertyOpt = propertyRepository.findById(listing.getPropertyId());
            if (updatedPropertyOpt.isPresent()) {
                Double updatedAmount = updatedPropertyOpt.get().getPrice();
                return new DiscountApplyResponse(updatedAmount != null ? updatedAmount : originalAmount, "You have used this discount code.");
            }
            return new DiscountApplyResponse(originalAmount, "You have used this discount code.");
        }

        Optional<DiscountCodeEntity> codeOpt = discountCodeRepository.findByCode(discountCode);
        if (codeOpt.isEmpty()) {
            return new DiscountApplyResponse(originalAmount, "Discount code does not exist.");
        }

        DiscountCodeEntity code = codeOpt.get();
        if (!code.getIsActive()) {
            return new DiscountApplyResponse(originalAmount, "Discount code is not active.");
        }
        if (LocalDateTime.now().isAfter(code.getExpiryDate())) {
            return new DiscountApplyResponse(originalAmount, "Discount code has expired.");
        }
        if (code.getMaxUses() != null && code.getUsedCount() >= code.getMaxUses()) {
            return new DiscountApplyResponse(originalAmount, "Discount code has reached its usage limit.");
        }

        // Áp dụng giảm giá
        Double discountedAmount = originalAmount;
        if ("percentage".equalsIgnoreCase(code.getDiscountType())) {
            discountedAmount = originalAmount - (originalAmount * code.getDiscountValue() / 100);
        } else if ("fixed".equalsIgnoreCase(code.getDiscountType())) {
            discountedAmount = originalAmount - code.getDiscountValue();
        }
        discountedAmount = Math.max(0, discountedAmount); // Đảm bảo không âm

        // Cập nhật giá mới vào Property
        property.setPrice(String.valueOf(discountedAmount));
        propertyRepository.save(property);

        // Ghi lại sử dụng mã
        DiscountUsageEntity usage = new DiscountUsageEntity(userId, code.getDiscountCodeId(), LocalDateTime.now());
        discountUsageRepository.save(usage);

        // Cập nhật usedCount
        code.setUsedCount(code.getUsedCount() + 1);
        discountCodeRepository.save(code);

        return new DiscountApplyResponse(discountedAmount, "Discount applied successfully.");
    }

    @Override
    public DiscountUsageResponse getUsedDiscountCodes(Integer userId) {
        if (userId == null || userRepository.findById(userId).isEmpty()) {
            return new DiscountUsageResponse(Collections.emptyList(), "User does not exist.");
        }

        List<DiscountUsageEntity> usages = discountUsageRepository.findByUserId(userId);
        if (usages.isEmpty()) {
            return new DiscountUsageResponse(Collections.emptyList(), "No discount codes used.");
        }

        List<String> usedCodes = usages.stream()
                .map(usage -> {
                    Optional<DiscountCodeEntity> codeOpt = discountCodeRepository.findById(usage.getDiscountCodeId());
                    return codeOpt.map(DiscountCodeEntity::getCode).orElse(null);
                })
                .filter(code -> code != null)
                .distinct()
                .collect(Collectors.toList());

        return new DiscountUsageResponse(usedCodes, "Discount codes retrieved successfully.");
    }
}