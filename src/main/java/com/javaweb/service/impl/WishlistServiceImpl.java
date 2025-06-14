package com.javaweb.service.impl;

import com.javaweb.repository.FavouriteListingRepository;
import com.javaweb.repository.ListingRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.repository.entity.FavouriteListingEntity;
import com.javaweb.repository.entity.ListingEntity;
import com.javaweb.repository.entity.UserEntity;
import com.javaweb.service.WishlistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {

    private static final Logger logger = LoggerFactory.getLogger(WishlistServiceImpl.class);

    @Autowired
    private FavouriteListingRepository favouriteListingRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, Object> addToWishlist(Integer userId, Integer listingId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UserEntity> userOpt = userRepository.findById(userId);
            Optional<ListingEntity> listingOpt = listingRepository.findById(listingId);
            if (userOpt.isEmpty() || listingOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "User or listing not found");
                return response;
            }
            UserEntity user = userOpt.get();
            ListingEntity listing = listingOpt.get();
            if (favouriteListingRepository.findByUserAndListing(user.getUserId(), listing.getListingId()).isPresent()) {
                response.put("success", false);
                response.put("message", "Already in wishlist");
                return response;
            }
            FavouriteListingEntity favourite = new FavouriteListingEntity();
            favourite.setUser(user);
            favourite.setListing(listing);
            favouriteListingRepository.save(favourite);
            response.put("success", true);
            response.put("message", "Added to your wishlist");
            return response;
        } catch (Exception e) {
            logger.error("Error adding to wishlist: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error adding to wishlist: " + e.getMessage());
            return response;
        }
    }

    @Override
    public Map<String, Object> removeFromWishlist(Integer userId, Integer listingId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UserEntity> userOpt = userRepository.findById(userId);
            Optional<ListingEntity> listingOpt = listingRepository.findById(listingId);
            if (userOpt.isEmpty() || listingOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "User or listing not found");
                return response;
            }
            UserEntity user = userOpt.get();
            ListingEntity listing = listingOpt.get();
            Optional<FavouriteListingEntity> favouriteOpt = favouriteListingRepository.findByUserAndListing(user.getUserId(), listing.getListingId());
            if (favouriteOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Not in wishlist");
                return response;
            }
            favouriteListingRepository.delete(favouriteOpt.get());
            response.put("success", true);
            response.put("message", "Property removed from your wishlist");
            return response;
        } catch (Exception e) {
            logger.error("Error removing from wishlist: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Error removing from wishlist: " + e.getMessage());
            return response;
        }
    }

    @Override
    public List<FavouriteListingEntity> getWishlist(Integer userId) {
        try {
            Optional<UserEntity> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                logger.warn("User not found: userId={}", userId);
                return List.of();
            }
            UserEntity user = userOpt.get();
            return favouriteListingRepository.findByUserId(user.getUserId());
        } catch (Exception e) {
            logger.error("Error retrieving wishlist: {}", e.getMessage());
            return List.of();
        }
    }
}