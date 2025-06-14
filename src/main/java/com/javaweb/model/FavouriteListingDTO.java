package com.javaweb.model;

public class FavouriteListingDTO {
    private Integer userId;
    private Integer listingId;
    private String title;
    private Double price;
    private String image;

    public FavouriteListingDTO() {}

    public FavouriteListingDTO(Integer userId, Integer listingId) {
        this.userId = userId;
        this.listingId = listingId;
    }

    public FavouriteListingDTO(Integer userId, Integer listingId, String title, Double price, String image) {
        this.userId = userId;
        this.listingId = listingId;
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getListingId() { return listingId; }
    public void setListingId(Integer listingId) { this.listingId = listingId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}