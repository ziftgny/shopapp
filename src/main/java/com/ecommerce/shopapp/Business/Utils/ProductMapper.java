package com.ecommerce.shopapp.Business.Utils;
import com.ecommerce.shopapp.Entities.Concretes.Product;
import com.ecommerce.shopapp.DTOs.Requests.ProductRequestDTO;
import com.ecommerce.shopapp.DTOs.Responses.ProductResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequestDTO dto);

    ProductResponseDTO toDto(Product product);

    List<ProductResponseDTO> toDtoList(List<Product> products);
}
