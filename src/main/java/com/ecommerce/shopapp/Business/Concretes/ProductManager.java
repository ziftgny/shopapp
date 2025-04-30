package com.ecommerce.shopapp.Business.Concretes;

import com.ecommerce.shopapp.Business.Abstracts.ProductService;
import com.ecommerce.shopapp.DataAccess.Abstracts.ProductRepository;
import com.ecommerce.shopapp.Entities.Concretes.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductManager implements ProductService {
    ProductRepository productRepository;

    public ProductManager(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
