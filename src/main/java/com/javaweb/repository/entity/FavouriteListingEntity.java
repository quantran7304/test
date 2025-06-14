package com.javaweb.repository.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "FavouriteListing")
public class FavouriteListingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FavouriteID")
    private Integer favouriteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ListingID", nullable = false)
    private ListingEntity listing;

    // Getters and Setters
    public Integer getFavouriteId() { return favouriteId; }
    public void setFavouriteId(Integer favouriteId) { this.favouriteId = favouriteId; }
    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }
    public ListingEntity getListing() { return listing; }
    public void setListing(ListingEntity listing) { this.listing = listing; }
}