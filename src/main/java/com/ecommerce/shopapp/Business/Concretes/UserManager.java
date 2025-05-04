package com.ecommerce.shopapp.Business.Concretes;

import com.ecommerce.shopapp.Business.Abstracts.UserService;
import com.ecommerce.shopapp.Core.Utils.Results.*;
import com.ecommerce.shopapp.DTOs.Requests.UserRequestDTO;

import com.ecommerce.shopapp.Business.Utils.UserMapper;
import com.ecommerce.shopapp.DataAccess.Abstracts.UserRepository;
import com.ecommerce.shopapp.Entities.Concretes.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserManager(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Result createUser(UserRequestDTO dto) {
        if (userExists(dto.getEmail())) {
            return new ErrorResult("Bu e-posta ile zaten kayıtlı bir kullanıcı var.");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("USER");

        userRepository.save(user);
        return new SuccessResult("Kayıt başarıyla oluşturuldu.");
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public DataResult<UserRequestDTO> getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return new SuccessDataResult<UserRequestDTO>(userMapper.toDTO(user));
    }

    @Override
    public DataResult<UserRequestDTO> getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return new SuccessDataResult<UserRequestDTO>(userMapper.toDTO(user));
    }
}

