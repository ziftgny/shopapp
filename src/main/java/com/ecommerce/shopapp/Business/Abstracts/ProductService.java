package com.ecommerce.shopapp.Business.Abstracts;

import com.ecommerce.shopapp.Core.Utils.Results.DataResult;
import com.ecommerce.shopapp.Core.Utils.Results.Result;
import com.ecommerce.shopapp.DTOs.Requests.ProductRequestDTO;
import com.ecommerce.shopapp.DTOs.Responses.ProductResponseDTO;
import com.ecommerce.shopapp.Entities.Concretes.Product;

import java.util.List;

public interface ProductService {
    DataResult<List<ProductResponseDTO>> getAll();
    Result create(ProductRequestDTO productRequestDTO);
    DataResult<ProductResponseDTO> getById(Long id);
    Result update(Long id, ProductRequestDTO dto);
    Result delete(Long id);
    void createWithStore(ProductRequestDTO dto, Long storeId);
    DataResult<List<ProductResponseDTO>> getProductsByStoreIds(List<Long> storeIds);
}
