package com.railway.booking.service;

import com.railway.booking.entity.User;
import com.railway.booking.model.UserEntity;
import org.springframework.data.domain.Page;

public interface UserService {

    User login(String email, String password);

    boolean register(UserEntity userEntity);

    Page<User> findAll(String pageNumber);

    User findById(Integer id);

    User findByEmail(String email);
}