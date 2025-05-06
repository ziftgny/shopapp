package com.ecommerce.shopapp.DTOs.Responses;

public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private StoreResponseDTO store; // Product dto'ya store dto'sunu ekledik(ürünün ait olduğu mağaza bilgisine erişmek için)

    public ProductResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StoreResponseDTO getStore() {
        return store;
    }

    public void setStore(StoreResponseDTO store) {
        this.store = store;
    }
}
