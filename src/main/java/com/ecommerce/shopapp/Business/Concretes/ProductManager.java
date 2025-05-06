package com.ecommerce.shopapp.Business.Concretes;

import com.ecommerce.shopapp.Business.Abstracts.ProductService;
import com.ecommerce.shopapp.Business.Utils.ProductMapper;
import com.ecommerce.shopapp.Core.Utils.Results.*;
import com.ecommerce.shopapp.DTOs.Requests.ProductRequestDTO;
import com.ecommerce.shopapp.DTOs.Responses.ProductResponseDTO;
import com.ecommerce.shopapp.DataAccess.Abstracts.ProductRepository;
import com.ecommerce.shopapp.DataAccess.Abstracts.StoreRepository;
import com.ecommerce.shopapp.Entities.Concretes.Product;
import com.ecommerce.shopapp.Entities.Concretes.Store;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductManager implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final StoreRepository storeRepository;


    public ProductManager(ProductRepository productRepository, ProductMapper productMapper, StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.storeRepository = storeRepository;
    }

    @Override
    public DataResult<List<ProductResponseDTO>> getAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> dtoList = productMapper.toDtoList(products);
        return new SuccessDataResult<>(dtoList, "Products Listed Successfully");
    }

    @Override
    public Result create(ProductRequestDTO productDto) {
        Product product = productMapper.toEntity(productDto);
        productRepository.save(product);
        return new SuccessResult("Product Added Successfully");
    }

    @Override
    public DataResult<ProductResponseDTO> getById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return new ErrorDataResult<>("Product Not Found");
        }
        return new SuccessDataResult<>(productMapper.toDto(product), "Product Found");
    }

    @Override
    public Result update(Long id, ProductRequestDTO dto) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return new ErrorResult("Product Not Found");
        }

        // DTO'dan gelen verilerle güncelle
        existingProduct.setName(dto.getName());
        existingProduct.setDescription(dto.getDescription());
        existingProduct.setPrice(dto.getPrice());
        existingProduct.setImageUrl(dto.getImageUrl());

        productRepository.save(existingProduct);
        return new SuccessResult("Product Updated Successfully");
    }

    @Override
    public Result delete(Long id) {
        if (!productRepository.existsById(id)) {
            return new ErrorResult("Product Not Found");
        }
        productRepository.deleteById(id);
        return new SuccessResult("Product Deleted Successfully");
    }

    @Override
    public void createWithStore(ProductRequestDTO dto, Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store Not Found"));

        Product product = productMapper.toEntity(dto);
        product.setStore(store);

        productRepository.save(product);
    }

    @Override
    public DataResult<List<ProductResponseDTO>> getProductsByStoreIds(List<Long> storeIds) {
        List<Product> products = productRepository.findAllByStoreIdIn(storeIds);
        List<ProductResponseDTO> dtoList = productMapper.toDtoList(products);
        return new SuccessDataResult<>(dtoList);
    }


}
