package com.railway.booking.service;

import com.railway.booking.model.Order;

import java.util.List;

public interface OrderService {

    Order getById(Integer id);

    void update(Order order);

    List<Order> findAll(int pageNumber);
}
