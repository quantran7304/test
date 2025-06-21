package com.javaweb.model;

public class FavouriteListingDTO {
    private Integer favouriteId;
    private Integer userId;
    private Integer listingId;
    private Double price;
    private String listingStatus;

    // Constructors
    public FavouriteListingDTO() {}

    public FavouriteListingDTO(Integer favouriteId, Integer userId, Integer listingId, Double price, String listingStatus) {
        this.favouriteId = favouriteId;
        this.userId = userId;
        this.listingId = listingId;
        this.price = price;
        this.listingStatus = listingStatus;
    }

    // Getters and Setters
    public Integer getFavouriteId() {
        return favouriteId;
    }

    public void setFavouriteId(Integer favouriteId) {
        this.favouriteId = favouriteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getListingId() {
        return listingId;
    }

    public void setListingId(Integer listingId) {
        this.listingId = listingId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getListingStatus() {
        return listingStatus;
    }

    public void setListingStatus(String listingStatus) {
        this.listingStatus = listingStatus;
    }
}