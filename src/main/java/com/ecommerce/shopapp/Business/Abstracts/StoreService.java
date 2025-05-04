package com.ecommerce.shopapp.Business.Abstracts;


import com.ecommerce.shopapp.Core.Utils.Results.DataResult;
import com.ecommerce.shopapp.DTOs.Requests.ProductRequestDTO;
import com.ecommerce.shopapp.DTOs.Requests.StoreRequestDTO;
import com.ecommerce.shopapp.Core.Utils.Results.Result;
import com.ecommerce.shopapp.DTOs.Responses.ProductResponseDTO;
import com.ecommerce.shopapp.DTOs.Responses.StoreResponseDTO;

import java.util.List;

public interface StoreService {
    DataResult<List<StoreResponseDTO>> getAllStoresByOwnerId( Long ownerId);
    Result createStore(StoreRequestDTO dto);
    DataResult<StoreResponseDTO> getStoreById(Long id);
    Result updateStore(Long id, StoreRequestDTO dto);
    Result deleteStore(Long id);
}

