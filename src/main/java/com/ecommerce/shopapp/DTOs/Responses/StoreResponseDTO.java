package com.ecommerce.shopapp.DTOs.Responses;

public class StoreResponseDTO {

    private Long id;
    private String storeName;
    private String description;
    private String bannerImageUrl;
    private String shopImageUrl;
    private String ownerEmail;

    public StoreResponseDTO() {
    }

    public StoreResponseDTO(Long id, String storeName, String description, String bannerImageUrl, String shopImageUrl, String ownerEmail) {
        this.id = id;
        this.storeName = storeName;
        this.description = description;
        this.bannerImageUrl = bannerImageUrl;
        this.shopImageUrl = shopImageUrl;
        this.ownerEmail = ownerEmail;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    public String getShopImageUrl() {
        return shopImageUrl;
    }

    public void setShopImageUrl(String shopImageUrl) {
        this.shopImageUrl = shopImageUrl;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}


