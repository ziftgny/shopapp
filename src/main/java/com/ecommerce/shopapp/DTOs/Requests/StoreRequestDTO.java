package com.ecommerce.shopapp.DTOs.Requests;

public class StoreRequestDTO {

    private String storeName;
    private String description;
    private String bannerImageUrl;
    private String shopImageUrl;
    private Long ownerId;

    public StoreRequestDTO() {
    }

    public StoreRequestDTO(String storeName, String description, String bannerImageUrl, String shopImageUrl, Long ownerId) {
        this.storeName = storeName;
        this.description = description;
        this.bannerImageUrl = bannerImageUrl;
        this.shopImageUrl = shopImageUrl;
        this.ownerId = ownerId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}

