package com.railway.booking.service;

import com.railway.booking.entity.User;
import com.railway.booking.domain.ModelUser;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationProvider;

import java.util.Optional;

public interface UserService extends AuthenticationProvider {

    boolean register(ModelUser modelUser);

    Page<User> findAll(String pageNumber);

    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);
}