package com.ecommerce.shopapp.DataAccess.Abstracts;


import com.ecommerce.shopapp.Entities.Concretes.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByStoreIdIn(List<Long> storeIds);
}
