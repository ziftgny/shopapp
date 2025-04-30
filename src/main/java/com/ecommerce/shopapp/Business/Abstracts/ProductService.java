package com.ecommerce.shopapp.Business.Abstracts;

import com.ecommerce.shopapp.Entities.Concretes.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
}
