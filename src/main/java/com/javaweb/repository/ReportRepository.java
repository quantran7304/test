package com.javaweb.repository;

import com.javaweb.repository.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {
    Optional<ReportEntity> findByUserIdAndListingId(Integer userId, Integer listingId);
    List<ReportEntity> findByUserId(Integer userId);
}