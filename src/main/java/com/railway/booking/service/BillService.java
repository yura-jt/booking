package com.railway.booking.service;

import com.railway.booking.model.Bill;
import org.springframework.data.domain.Page;

public interface BillService {

    void payBill(Bill bill);

    Bill getById(Integer id);

    Page<Bill> findAll(String pageNumber);

    void update(Bill bill);
}
