package com.ecommerce.shopapp.DataAccess.Abstracts;


import com.ecommerce.shopapp.Entities.Concretes.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByOwnerId(Long ownerId);
    Store findByStoreName(String storeName);
    Store findBySlug(String slug);
}
