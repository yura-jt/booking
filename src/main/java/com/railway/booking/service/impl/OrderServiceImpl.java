package com.railway.booking.service.impl;

import com.railway.booking.entity.Order;
import com.railway.booking.repository.OrderRepository;
import com.railway.booking.service.OrderService;
import com.railway.booking.service.PageProvider;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.validator.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final PageProvider pageProvider;

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> getById(Integer id) {
        orderValidator.validateId(id);
        return orderRepository.findById(id);
    }

    @Override
    @Transactional
    public void update(Order order) {
        orderValidator.isValid(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> findAll(String page) {
        int currentPage = pageProvider.getPageNumberFromString(page);

        int evalPage = (currentPage < 1) ? 1 : (currentPage - 1);
        evalPage = evalPage > pageProvider.getMaxPage(Constants.ITEM_PER_PAGE,
                (int) orderRepository.count()) ? 0 : evalPage;

        return orderRepository.findAll(PageRequest.of(evalPage, Constants.ITEM_PER_PAGE));
    }
}