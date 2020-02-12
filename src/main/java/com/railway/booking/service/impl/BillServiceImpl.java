package com.railway.booking.service.impl;

import com.railway.booking.model.Bill;
import com.railway.booking.model.BillStatus;
import com.railway.booking.repository.BillRepository;
import com.railway.booking.service.BillService;
import com.railway.booking.service.PageUtil;
import com.railway.booking.service.validator.BillValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BillServiceImpl implements BillService {
    private static final Logger LOGGER = LogManager.getLogger(BillServiceImpl.class);

    private static final int BILL_PER_PAGE = 5;

    private final BillRepository billRepository;
    private final BillValidator billValidator;
    private final PageUtil pageUtil;

    @Autowired
    public BillServiceImpl(BillRepository billRepository, BillValidator billValidator, PageUtil pageUtil) {
        this.billRepository = billRepository;
        this.billValidator = billValidator;
        this.pageUtil = pageUtil;
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
    public Page<Bill> findAll(String page) {
        int currentPage = pageUtil.getPageNumberFromString(page);

        int evalPage = (currentPage < 1) ? 1 : (currentPage - 1);

        return billRepository.findAll(PageRequest.of(evalPage, BILL_PER_PAGE));
    }
}