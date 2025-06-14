package com.javaweb.repository;

import com.javaweb.repository.entity.FavouriteListingEntity;
import com.javaweb.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteListingRepository extends JpaRepository<FavouriteListingEntity, Integer> {
    @Query("SELECT f FROM FavouriteListingEntity f WHERE f.user.id = :userId AND f.listing.listingId = :listingId")
    Optional<FavouriteListingEntity> findByUserAndListing(@Param("userId") Integer userId, @Param("listingId") Integer listingId);

    @Query("SELECT f FROM FavouriteListingEntity f WHERE f.user.id = :userId")
    List<FavouriteListingEntity> findByUserId(@Param("userId") Integer userId);
}