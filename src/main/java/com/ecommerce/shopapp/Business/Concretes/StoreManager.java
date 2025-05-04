package com.ecommerce.shopapp.Business.Concretes;


import com.ecommerce.shopapp.Business.Abstracts.StoreService;
import com.ecommerce.shopapp.Business.Abstracts.UserService;
import com.ecommerce.shopapp.Business.Utils.StoreMapper;
import com.ecommerce.shopapp.Business.Utils.UserMapper;
import com.ecommerce.shopapp.Core.Utils.Results.*;
import com.ecommerce.shopapp.DTOs.Requests.UserRequestDTO;
import com.ecommerce.shopapp.DTOs.Responses.ProductResponseDTO;
import com.ecommerce.shopapp.DTOs.Responses.StoreResponseDTO;
import com.ecommerce.shopapp.DataAccess.Abstracts.StoreRepository;
import com.ecommerce.shopapp.DataAccess.Abstracts.UserRepository;
import com.ecommerce.shopapp.DTOs.Requests.StoreRequestDTO;
import com.ecommerce.shopapp.Entities.Concretes.Product;
import com.ecommerce.shopapp.Entities.Concretes.Store;
import com.ecommerce.shopapp.Entities.Concretes.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreManager implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;
    private final StoreMapper storeMapper;
    private final UserMapper userMapper;

    public StoreManager(StoreRepository storeRepository, UserService userService, StoreMapper storeMapper, UserMapper userMapper) {
        this.storeRepository = storeRepository;
        this.userService = userService;
        this.storeMapper = storeMapper;
        this.userMapper = userMapper;
    }


    @Override
    public DataResult<List<StoreResponseDTO>> getAllStoresByOwnerId(Long ownerId) {
        List<Store> stores = storeRepository.findByOwnerId(ownerId);
        return new SuccessDataResult<>(storeMapper.toDtoList(stores));
    }

    @Override
    public Result createStore(StoreRequestDTO dto) {
        UserRequestDTO ownerDTO = userService.getUserById(dto.getOwnerId()).getData();
        if (ownerDTO == null) {
            return new ErrorResult("Owner not found");
        }

        Store store = storeMapper.toEntity(dto);
        User owner = userMapper.toEntity(ownerDTO);
        store.setOwner(owner);
        storeRepository.save(store);

        return new SuccessResult("Store added successfully");
    }

    @Override
    public DataResult<StoreResponseDTO> getStoreById(Long id) {
        Store store = storeRepository.findById(id).orElse(null);
        if (store == null) {
            return new ErrorDataResult<>("Store not found");
        }
        return new SuccessDataResult<>(storeMapper.toDto(store));
    }

    @Override
    public Result updateStore(Long id, StoreRequestDTO dto) {
        Store store = storeRepository.findById(id).orElse(null);
        if (!(store.getOwner().getId().equals(dto.getOwnerId()))) {
            return new ErrorResult("Owner does not belong to this store");
        }
        if (store == null) {
            return new ErrorResult("Store not found");
        }
        if (dto.getBannerImageUrl() != null) {
            store.setBannerImageUrl(dto.getBannerImageUrl());
        }
        if (dto.getStoreName() != null) {
            store.setStoreName(dto.getStoreName());
        }
        store.setStoreName(dto.getStoreName());
        store.setDescription(dto.getDescription());
        storeRepository.save(store);

        return new SuccessResult("Store updated successfully");
    }

    @Override
    public Result deleteStore(Long id) {
        if (!storeRepository.existsById(id)) {
            return new ErrorResult("Store not found");
        }

        storeRepository.deleteById(id);
        return new SuccessResult("Store deleted successfully");
    }

}
