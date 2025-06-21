package com.javaweb.model;

import java.util.List;

public class FavouriteListingResponseDTO {
    private List<FavouriteListingDTO> data;
    private boolean success;
    private String message;

    // Constructors
    public FavouriteListingResponseDTO() {}

    public FavouriteListingResponseDTO(List<FavouriteListingDTO> data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    // Getters and Setters
    public List<FavouriteListingDTO> getData() {
        return data;
    }

    public void setData(List<FavouriteListingDTO> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}