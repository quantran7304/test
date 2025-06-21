package com.javaweb.service;

import com.javaweb.model.FavouriteListingDTO;
import com.javaweb.model.FavouriteListingResponseDTO;

public interface FavouriteListingService {
    FavouriteListingResponseDTO addToFavourite(FavouriteListingDTO request);
    FavouriteListingResponseDTO removeFromFavourite(FavouriteListingDTO request);
    FavouriteListingResponseDTO getFavouriteList(Integer userId);
}