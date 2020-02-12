package com.railway.booking.service.impl;

import com.railway.booking.model.Order;
import com.railway.booking.repository.OrderRepository;
import com.railway.booking.repository.domain.Page;
import com.railway.booking.service.OrderService;
import com.railway.booking.service.validator.OrderValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    private static final Integer MAX_ORDER_PER_PAGE = 5;

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderValidator orderValidator) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
    }

    @Override
    @Transactional(readOnly = true)
    public Order getById(Integer id) {
        orderValidator.validateId(id);
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void update(Order order) {
        orderValidator.isValid(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll(int pageNumber) {
        int maxPage = getMaxPage();
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }
        new Page(pageNumber, MAX_ORDER_PER_PAGE);
        return orderRepository.findAll();
    }

    private int getMaxPage() {
        int totalUsers = (int) orderRepository.count();
        int page = totalUsers / MAX_ORDER_PER_PAGE;
        if (totalUsers % MAX_ORDER_PER_PAGE != 0) {
            page++;
        }
        return page == 0 ? 1 : page;
    }
}