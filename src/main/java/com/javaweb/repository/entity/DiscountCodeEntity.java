package com.javaweb.repository.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "DiscountCode")
public class DiscountCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiscountCodeID")
    private Integer discountCodeId;

    @Column(name = "Code", nullable = false, unique = true)
    private String code;

    @Column(name = "DiscountType", nullable = false)
    private String discountType; // "percentage" or "fixed"

    @Column(name = "Value", nullable = false)
    private Double value; // Percentage (e.g., 10.0) or fixed amount (e.g., 5.0)

    @Column(name = "ExpirationDate")
    private LocalDateTime expirationDate;

    @Column(name = "UsageLimit")
    private Integer usageLimit;

    @Column(name = "UsedCount")
    private Integer usedCount;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive = true;

    // Constructors
    public DiscountCodeEntity() {}

    public DiscountCodeEntity(String code, String discountType, Double value, LocalDateTime expirationDate, Integer usageLimit, Integer usedCount) {
        this.code = code;
        this.discountType = discountType;
        this.value = value;
        this.expirationDate = expirationDate;
        this.usageLimit = usageLimit;
        this.usedCount = usedCount != null ? usedCount : 0;
    }

    // Getters and Setters
    public Integer getDiscountCodeId() {
        return discountCodeId;
    }

    public void setDiscountCodeId(Integer discountCodeId) {
        this.discountCodeId = discountCodeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Integer getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}