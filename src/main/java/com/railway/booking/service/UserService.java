package com.railway.booking.service;

import com.railway.booking.entity.UserDto;
import com.railway.booking.model.User;

import java.util.List;

public interface UserService {

    User login(String email, String password);

    void register(UserDto userDto);

    List<User> findAll(int pageNumber);

    User findById(Integer id);

    User findByEmail(String email);
}