package com.javaweb.repository;

import com.javaweb.repository.entity.DiscountCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountCodeRepository extends JpaRepository<DiscountCodeEntity, Integer> {
    Optional<DiscountCodeEntity> findByCode(String code);
}