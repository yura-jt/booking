package com.railway.booking.service;

import com.railway.booking.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

    Order getById(Integer id);

    void update(Order order);

    Page<Order> findAll(String pageNumber);
}
