package com.railway.booking.service;

import com.railway.booking.entity.Bill;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BillService {

    void payBill(Bill bill);

    Optional<Bill> getById(Integer id);

    Page<Bill> findAll(String pageNumber);

    void update(Bill bill);
}
