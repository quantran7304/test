package com.javaweb.service.impl;

import com.javaweb.model.FavouriteListingDTO;
import com.javaweb.model.FavouriteListingResponseDTO;
import com.javaweb.repository.FavouriteListingRepository;
import com.javaweb.repository.ListingRepository;
import com.javaweb.repository.PropertyRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.repository.entity.FavouriteListingEntity;
import com.javaweb.repository.entity.ListingEntity;
import com.javaweb.repository.entity.PropertyEntity;
import com.javaweb.repository.entity.UserEntity;
import com.javaweb.service.FavouriteListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavouriteListingServiceImpl implements FavouriteListingService {

    @Autowired
    private FavouriteListingRepository favouriteListingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private boolean validateFavouriteAction(FavouriteListingDTO request) {
        if (request == null) {
            return false;
        }
        Integer userId = request.getUserId();
        if (userId == null) {
            return false;
        }
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return false;
        }
        Integer listingId = request.getListingId();
        if (listingId == null) {
            return false;
        }
        Optional<ListingEntity> listingOpt = listingRepository.findById(listingId);
        if (listingOpt.isEmpty()) {
            return false;
        }
        if (listingOpt.get().getUserId() != null && listingOpt.get().getUserId().equals(userId)) {
            return false;
        }
        return true;
    }

    @Override
    public FavouriteListingResponseDTO addToFavourite(FavouriteListingDTO request) {
        if (request == null) {
            return new FavouriteListingResponseDTO(null, false, "Request body is missing.");
        }
        if (!validateFavouriteAction(request)) {
            return new FavouriteListingResponseDTO(null, false, "Invalid input or user cannot add own listing.");
        }

        Optional<FavouriteListingEntity> existingFavourite = favouriteListingRepository.findByUserIdAndListingId(
                request.getUserId(), request.getListingId());
        if (existingFavourite.isPresent()) {
            return new FavouriteListingResponseDTO(null, false, "Listing already in favourite list.");
        }

        FavouriteListingEntity favourite = new FavouriteListingEntity(request.getUserId(), request.getListingId());
        try {
            FavouriteListingEntity savedFavourite = favouriteListingRepository.save(favourite);
            if (savedFavourite == null || savedFavourite.getFavouriteId() == null) {
                return new FavouriteListingResponseDTO(null, false, "Failed to save favourite listing.");
            }
            return new FavouriteListingResponseDTO(null, true, "Listing added to favourite list successfully.");
        } catch (Exception e) {
            return new FavouriteListingResponseDTO(null, false, "Unable to add to favourite list. Please try again later.");
        }
    }

    @Override
    public FavouriteListingResponseDTO removeFromFavourite(FavouriteListingDTO request) {
        if (request == null) {
            return new FavouriteListingResponseDTO(null, false, "Request body is missing.");
        }
        if (!validateFavouriteAction(request)) {
            return new FavouriteListingResponseDTO(null, false, "Invalid input.");
        }

        Optional<FavouriteListingEntity> favouriteOpt = favouriteListingRepository.findByUserIdAndListingId(
                request.getUserId(), request.getListingId());
        if (favouriteOpt.isEmpty()) {
            return new FavouriteListingResponseDTO(null, false, "Listing not found in favourite list.");
        }

        try {
            favouriteListingRepository.delete(favouriteOpt.get());
            return new FavouriteListingResponseDTO(null, true, "Listing removed from favourite list successfully.");
        } catch (Exception e) {
            return new FavouriteListingResponseDTO(null, false, "Unable to remove from favourite list. Please try again later.");
        }
    }

    @Override
    public FavouriteListingResponseDTO getFavouriteList(Integer userId) {
        if (userId == null) {
            return new FavouriteListingResponseDTO(null, false, "User ID is required.");
        }
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return new FavouriteListingResponseDTO(null, false, "User does not exist.");
        }

        List<FavouriteListingEntity> favourites = favouriteListingRepository.findByUserId(userId);
        if (favourites.isEmpty()) {
            return new FavouriteListingResponseDTO(Collections.emptyList(), true, "No listings in favourite list.");
        }

        List<FavouriteListingDTO> favouriteDTOs = favourites.stream()
                .sorted((f1, f2) -> f1.getFavouriteId().compareTo(f2.getFavouriteId())) // Sắp xếp tăng dần theo FavouriteID
                .map(favourite -> {
                    Optional<ListingEntity> listingOpt = listingRepository.findById(favourite.getListingId());
                    if (listingOpt.isPresent()) {
                        ListingEntity listing = listingOpt.get();
                        Integer propertyId = listing.getPropertyId();
                        Double price = (propertyId != null)
                                ? propertyRepository.findById(propertyId).map(PropertyEntity::getPrice).orElse(0.0)
                                : 0.0;
                        return new FavouriteListingDTO(
                                favourite.getFavouriteId(),
                                favourite.getUserId(),
                                listing.getListingId(),
                                price,
                                listing.getListingStatus()
                        );
                    }
                    return null;
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());

        return new FavouriteListingResponseDTO(favouriteDTOs, true, "Favourite list retrieved successfully.");
    }
}