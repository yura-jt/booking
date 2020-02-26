package com.railway.booking.service.impl;

import com.railway.booking.entity.Bill;
import com.railway.booking.entity.BillStatus;
import com.railway.booking.repository.BillRepository;
import com.railway.booking.service.BillService;
import com.railway.booking.service.PageProvider;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.validator.BillValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final BillValidator billValidator;
    private final PageProvider pageProvider;

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
    public Optional<Bill> getById(Integer id) {
        billValidator.validateId(id);
        return billRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bill> findAll(String page) {
        int currentPage = pageProvider.getPageNumberFromString(page);

        int evalPage = (currentPage < 1) ? 1 : (currentPage - 1);
        evalPage = evalPage > pageProvider.getMaxPage(Constants.ITEM_PER_PAGE,
                (int) billRepository.count()) ? 0 : evalPage;

        return billRepository.findAll(PageRequest.of(evalPage, Constants.ITEM_PER_PAGE));
    }
}