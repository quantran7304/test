package com.javaweb.repository;

import com.javaweb.repository.entity.DiscountUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountUsageRepository extends JpaRepository<DiscountUsageEntity, Integer> {
    List<DiscountUsageEntity> findByUserId(Integer userId);
    @Query("SELECT u FROM DiscountUsageEntity u WHERE u.userId = :userId AND u.discountCodeId = :discountCodeId")
    List<DiscountUsageEntity> findByUserIdAndDiscountCodeId(@Param("userId") Integer userId, @Param("discountCodeId") Integer discountCodeId);
}