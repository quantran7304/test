package com.javaweb.service;

import com.javaweb.repository.entity.FavouriteListingEntity;

import java.util.List;
import java.util.Map;

public interface WishlistService {
    Map<String, Object> addToWishlist(Integer userId, Integer listingId);
    Map<String, Object> removeFromWishlist(Integer userId, Integer listingId);
    List<FavouriteListingEntity> getWishlist(Integer userId);
}