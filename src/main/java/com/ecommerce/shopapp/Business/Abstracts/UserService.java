package com.ecommerce.shopapp.Business.Abstracts;


import com.ecommerce.shopapp.Core.Utils.Results.DataResult;
import com.ecommerce.shopapp.DTOs.Requests.UserRequestDTO;
import com.ecommerce.shopapp.Entities.Concretes.User;
import com.ecommerce.shopapp.Core.Utils.Results.Result;

public interface UserService {
    Result createUser(UserRequestDTO dto);
    boolean userExists(String email);
    DataResult<UserRequestDTO> getUserByEmail(String email);
    DataResult<UserRequestDTO> getUserById(Long id);

}
