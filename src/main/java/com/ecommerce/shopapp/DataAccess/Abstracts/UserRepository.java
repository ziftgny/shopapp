package com.ecommerce.shopapp.DataAccess.Abstracts;

import com.ecommerce.shopapp.Entities.Concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
