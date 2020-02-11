package com.railway.booking.service.impl;

import com.railway.booking.model.Bill;
import com.railway.booking.model.BillStatus;
import com.railway.booking.repository.BillRepository;
import com.railway.booking.service.BillService;
import com.railway.booking.service.validator.BillValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BillServiceImpl implements BillService {
    private static final Logger LOGGER = LogManager.getLogger(BillServiceImpl.class);

    private final BillRepository billRepository;
    private final BillValidator billValidator;

    @Autowired
    public BillServiceImpl(BillRepository billRepository, BillValidator billValidator) {
        this.billRepository = billRepository;
        this.billValidator = billValidator;
    }

    @Override
    public void payBill(Bill bill) {
        billValidator.isValid(bill);
        Bill newBill = new Bill();
        bill.setBillStatus(BillStatus.PAID);
        billRepository.save(newBill);
    }

    @Override
    public void update(Bill bill) {
        billValidator.isValid(bill);

        billRepository.save(bill);
    }

    @Override
    @Transactional(readOnly = true)
    public Bill getById(Integer id) {
        billValidator.validateId(id);
        return billRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bill> findAll(int pageNumber) {
        return billRepository.findAll();
    }
}