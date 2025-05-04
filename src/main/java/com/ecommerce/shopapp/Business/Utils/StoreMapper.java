package com.ecommerce.shopapp.Business.Utils;



import com.ecommerce.shopapp.DTOs.Requests.StoreRequestDTO;
import com.ecommerce.shopapp.DTOs.Responses.StoreResponseDTO;
import com.ecommerce.shopapp.Entities.Concretes.Store;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    Store toEntity(StoreRequestDTO dto);
    List<StoreResponseDTO> toDtoList(List<Store> entities);
    StoreResponseDTO toDto(Store entity);
}
