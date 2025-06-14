package com.javaweb.repository;

import com.javaweb.repository.entity.ReportEntity;
import com.javaweb.repository.entity.UserEntity;
import com.javaweb.repository.entity.ListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {
    Optional<ReportEntity> findByUserAndListing(UserEntity user, ListingEntity listing);
}