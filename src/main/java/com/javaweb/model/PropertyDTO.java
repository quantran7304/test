package com.javaweb.model;

public class PropertyDTO {

    private Integer propertyID;
    private String addressLine1;
    private String addressLine2;
    private String region;
    private String city;
    private Double area;
    private Double interior;
    private String propertyType;
    private Integer numBedroom;
    private Integer numCompares;
    private Integer numBathroom;
    private Integer floor;
    private Boolean privatePool;
    private String landType;
    private String legalStatus;
    private String imgURL;
    private String googleMapEmbedUrl; // Dùng để hiển thị bản đồ

    // ======= Getters & Setters =======

    public Integer getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(Integer propertyID) {
        this.propertyID = propertyID;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getInterior() {
        return interior;
    }

    public void setInterior(Double interior) {
        this.interior = interior;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getNumBedroom() {
        return numBedroom;
    }

    public void setNumBedroom(Integer numBedroom) {
        this.numBedroom = numBedroom;
    }

    public Integer getNumCompares() {
        return numCompares;
    }

    public void setNumCompares(Integer numCompares) {
        this.numCompares = numCompares;
    }

    public Integer getNumBathroom() {
        return numBathroom;
    }

    public void setNumBathroom(Integer numBathroom) {
        this.numBathroom = numBathroom;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Boolean getPrivatePool() {
        return privatePool;
    }

    public void setPrivatePool(Boolean privatePool) {
        this.privatePool = privatePool;
    }

    public String getLandType() {
        return landType;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public String getLegalStatus() {
        return legalStatus;
    }

    public void setLegalStatus(String legalStatus) {
        this.legalStatus = legalStatus;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getGoogleMapEmbedUrl() {
        return googleMapEmbedUrl;
    }

    public void setGoogleMapEmbedUrl(String googleMapEmbedUrl) {
        this.googleMapEmbedUrl = googleMapEmbedUrl;
    }
}
