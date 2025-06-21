package com.javaweb.repository.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Listing")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ListingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Listing_ID")
    private Integer listingId;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Listing_status")
    private String listingStatus;

    @Column(name = "Listing_type")
    private String listingType;

    @Column(name = "PropertyID")
    private Integer propertyId;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Img_url")
    private String imgUrl;

    @Column(name = "UserID")
    private Integer userId;

    // === Constructors ===

    public ListingEntity() {
    }

    public ListingEntity(Integer listingId, String description, String listingStatus, String listingType, Integer propertyId, Double price, String imgUrl, Integer userId) {
        this.listingId = listingId;
        this.description = description;
        this.listingStatus = listingStatus;
        this.listingType = listingType;
        this.propertyId = propertyId;
        this.price = price;
        this.imgUrl = imgUrl;
        this.userId = userId;
    }

    // === Getters & Setters ===

    public Integer getListingId() {
        return listingId;
    }

    public void setListingId(Integer listingId) {
        this.listingId = listingId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getListingStatus() {
        return listingStatus;
    }

    public void setListingStatus(String listingStatus) {
        this.listingStatus = listingStatus;
    }

    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}