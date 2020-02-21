package com.railway.booking.service;

import com.railway.booking.entity.Order;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface OrderService {

    Optional<Order> getById(Integer id);

    void update(Order order);

    Page<Order> findAll(String pageNumber);
}
