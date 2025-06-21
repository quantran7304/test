package com.javaweb.repository;

import com.javaweb.repository.entity.FavouriteListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteListingRepository extends JpaRepository<FavouriteListingEntity, Integer> {
    Optional<FavouriteListingEntity> findByUserIdAndListingId(Integer userId, Integer listingId);
    List<FavouriteListingEntity> findByUserId(Integer userId);
}