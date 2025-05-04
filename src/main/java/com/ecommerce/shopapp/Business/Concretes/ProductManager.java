package com.ecommerce.shopapp.Business.Concretes;

import com.ecommerce.shopapp.Business.Abstracts.ProductService;
import com.ecommerce.shopapp.Business.Utils.ProductMapper;
import com.ecommerce.shopapp.Core.Utils.Results.*;
import com.ecommerce.shopapp.DTOs.Requests.ProductRequestDTO;
import com.ecommerce.shopapp.DTOs.Responses.ProductResponseDTO;
import com.ecommerce.shopapp.DataAccess.Abstracts.ProductRepository;
import com.ecommerce.shopapp.Entities.Concretes.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductManager implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductManager(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public DataResult<List<ProductResponseDTO>> getAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> dtoList = productMapper.toDtoList(products);
        return new SuccessDataResult<>(dtoList, "Ürünler başarıyla listelendi.");
    }

    @Override
    public Result create(ProductRequestDTO productDto) {
        Product product = productMapper.toEntity(productDto);
        productRepository.save(product);
        return new SuccessResult("Ürün başarıyla eklendi.");
    }

    @Override
    public DataResult<ProductResponseDTO> getById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return new ErrorDataResult<>("Ürün bulunamadı.");
        }
        return new SuccessDataResult<>(productMapper.toDto(product), "Ürün getirildi.");
    }

    @Override
    public Result update(Long id, ProductRequestDTO dto) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return new ErrorResult("Güncellenecek ürün bulunamadı.");
        }

        // DTO'dan gelen verilerle güncelle
        existingProduct.setName(dto.getName());
        existingProduct.setDescription(dto.getDescription());
        existingProduct.setPrice(dto.getPrice());
        existingProduct.setImageUrl(dto.getImageUrl());

        productRepository.save(existingProduct);
        return new SuccessResult("Ürün başarıyla güncellendi.");
    }

    @Override
    public Result delete(Long id) {
        if (!productRepository.existsById(id)) {
            return new ErrorResult("Silinecek ürün bulunamadı.");
        }
        productRepository.deleteById(id);
        return new SuccessResult("Ürün başarıyla silindi.");
    }
}
