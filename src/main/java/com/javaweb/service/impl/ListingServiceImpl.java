package com.javaweb.service.impl;

import com.javaweb.model.ListingDTO;
import com.javaweb.repository.FavouriteListingRepository;
import com.javaweb.repository.ListingRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.repository.entity.FavouriteListingEntity;
import com.javaweb.repository.entity.ListingEntity;
import com.javaweb.repository.entity.UserEntity;
import com.javaweb.service.ListingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService {

    private static final Logger logger = LoggerFactory.getLogger(ListingServiceImpl.class);

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavouriteListingRepository favouriteListingRepository;

    public Optional<ListingDTO> getListingByPropertyId(Integer propertyId) {
        // Lưu ý: Phương thức này hiện tại tìm theo Listing_ID, không phải PropertyID. Sử dụng getListingsByPropertyId thay thế nếu cần.
        Optional<ListingEntity> entity = listingRepository.findById(propertyId);
        return entity.map(this::convertToDTO);
    }

    public List<ListingDTO> getAll() {
        return listingRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ListingDTO convertToDTO(ListingEntity entity) {
        return new ListingDTO(
                entity.getListingId(),
                entity.getDescription(),
                entity.getListingStatus(),
                entity.getPropertyId()
        );
    }

    public Optional<ListingDTO> getListingById(Integer listingId) {
        logger.info("Retrieving listing by listingId={}", listingId);
        Optional<ListingEntity> entity = listingRepository.findById(listingId);
        return entity.map(this::convertToDTO);
    }

    public boolean isInWishlist(Integer userId, Integer listingId) {
        logger.info("Checking wishlist status: userId={}, listingId={}", userId, listingId);
        Optional<UserEntity> user = userRepository.findById(userId);
        Optional<ListingEntity> listing = listingRepository.findById(listingId);
        if (user.isEmpty() || listing.isEmpty()) {
            logger.warn("User or listing not found: userId={}, listingId={}", userId, listingId);
            return false;
        }
        Optional<FavouriteListingEntity> favourite = favouriteListingRepository.findByUserAndListing(user.get().getUserId(), listing.get().getListingId());
        return favourite.isPresent();
    }

    public List<ListingDTO> getListingsByPropertyId(Integer propertyId) {
        logger.info("Retrieving listings by propertyId={}", propertyId);
        List<ListingEntity> entities = listingRepository.findByPropertyId(propertyId);
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}