package com.javaweb.controller;

import com.javaweb.service.WishlistService;
import com.javaweb.model.FavouriteListingDTO;
import com.javaweb.repository.entity.FavouriteListingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToWishlist(@RequestParam Integer userId, @RequestParam Integer listingId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> result = wishlistService.addToWishlist(userId, listingId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error adding to wishlist: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, Object>> removeFromWishlist(@RequestParam Integer userId, @RequestParam Integer listingId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> result = wishlistService.removeFromWishlist(userId, listingId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error removing from wishlist: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getWishlist(@RequestParam Integer userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<FavouriteListingEntity> favourites = wishlistService.getWishlist(userId);
            List<FavouriteListingDTO> wishlist = favourites.stream()
                    .map(f -> {
                        String image = f.getListing().getProperty().getImages().isEmpty()
                                ? f.getListing().getProperty().getImgURL()
                                : f.getListing().getProperty().getImages().get(0).getImageUrl();
                        return new FavouriteListingDTO(
                                userId,
                                f.getListing().getListingId(),
                                f.getListing().getDescription(),
                                f.getListing().getPrice(),
                                image
                        );
                    })
                    .collect(Collectors.toList());
            response.put("success", true);
            response.put("message", "Wishlist retrieved successfully");
            response.put("data", wishlist);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error retrieving wishlist: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}