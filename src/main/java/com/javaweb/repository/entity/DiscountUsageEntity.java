package com.javaweb.repository.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "DiscountUsage")
public class DiscountUsageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UsageID")
    private Integer usageId;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "DiscountCodeID")
    private Integer discountCodeId;

    @Column(name = "UsedDate")
    private LocalDateTime usedDate;

    // Constructor mặc định (yêu cầu của JPA)
    public DiscountUsageEntity() {
    }

    // Constructor với tham số
    public DiscountUsageEntity(Integer userId, Integer discountCodeId, LocalDateTime usedDate) {
        this.userId = userId;
        this.discountCodeId = discountCodeId;
        this.usedDate = usedDate;
    }

    // Getters and Setters
    public Integer getUsageId() {
        return usageId;
    }

    public void setUsageId(Integer usageId) {
        this.usageId = usageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDiscountCodeId() {
        return discountCodeId;
    }

    public void setDiscountCodeId(Integer discountCodeId) {
        this.discountCodeId = discountCodeId;
    }

    public LocalDateTime getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(LocalDateTime usedDate) {
        this.usedDate = usedDate;
    }
}