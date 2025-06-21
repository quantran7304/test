package com.javaweb.controller;

import com.javaweb.model.FavouriteListingDTO;
import com.javaweb.model.FavouriteListingResponseDTO;
import com.javaweb.service.FavouriteListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favourites")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class FavouriteListingController {

    @Autowired
    private FavouriteListingService favouriteListingService;

    // API POST để thêm bài đăng vào favourite list
    @PostMapping("/add")
    public ResponseEntity<FavouriteListingResponseDTO> addToFavourite(@RequestBody(required = false) FavouriteListingDTO request) {
        FavouriteListingResponseDTO response = favouriteListingService.addToFavourite(request);
        return ResponseEntity.ok(response);
    }

    // API POST để xóa bài đăng khỏi favourite list
    @PostMapping("/remove")
    public ResponseEntity<FavouriteListingResponseDTO> removeFromFavourite(@RequestBody(required = false) FavouriteListingDTO request) {
        FavouriteListingResponseDTO response = favouriteListingService.removeFromFavourite(request);
        return ResponseEntity.ok(response);
    }

    // API GET để xem danh sách favourite list
    @GetMapping("/list")
    public ResponseEntity<FavouriteListingResponseDTO> getFavouriteList(@RequestParam Integer userId) {
        FavouriteListingResponseDTO response = favouriteListingService.getFavouriteList(userId);
        if ("User does not exist.".equals(response.getMessage())) {
            return ResponseEntity.status(404).body(response);
        }
        return ResponseEntity.ok(response);
    }
}