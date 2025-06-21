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

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "DiscountType")
    private String discountType;

    @Column(name = "DiscountValue")
    private Double discountValue;

    @Column(name = "ExpiryDate")
    private LocalDateTime expiryDate;

    @Column(name = "MaxUses")
    private Integer maxUses;

    @Column(name = "UsedCount")
    private Integer usedCount;

    @Column(name = "IsActive")
    private Boolean isActive;

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

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(Integer maxUses) {
        this.maxUses = maxUses;
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