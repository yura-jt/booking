package com.railway.booking.service.impl;

import com.railway.booking.entity.Bill;
import com.railway.booking.entity.BillStatus;
import com.railway.booking.repository.BillRepository;
import com.railway.booking.service.BillService;
import com.railway.booking.service.PageProvider;
import com.railway.booking.service.validator.BillValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BillServiceImpl implements BillService {
    private static final int BILL_PER_PAGE = 5;

    private final BillRepository billRepository;
    private final BillValidator billValidator;
    private final PageProvider pageProvider;

    @Autowired
    public BillServiceImpl(BillRepository billRepository, BillValidator billValidator, PageProvider pageProvider) {
        this.billRepository = billRepository;
        this.billValidator = billValidator;
        this.pageProvider = pageProvider;
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
        int currentPage = pageProvider.getPageNumberFromString(page);

        int evalPage = (currentPage < 1) ? 1 : (currentPage - 1);
        evalPage = evalPage > pageProvider.getMaxPage(BILL_PER_PAGE, (int) billRepository.count()) ? 0 : evalPage;

        return billRepository.findAll(PageRequest.of(evalPage, BILL_PER_PAGE));
    }
}