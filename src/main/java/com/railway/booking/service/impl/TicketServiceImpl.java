package com.railway.booking.service.impl;

import com.railway.booking.entity.Ticket;
import com.railway.booking.repository.TicketRepository;
import com.railway.booking.service.PageProvider;
import com.railway.booking.service.TicketService;
import com.railway.booking.service.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final PageProvider pageProvider;

    @Override
    @Transactional(readOnly = true)
    public Page<Ticket> findAll(String page) {
        int currentPage = pageProvider.getPageNumberFromString(page);

        int evalPage = (currentPage < 1) ? 1 : (currentPage - 1);
        evalPage = evalPage > pageProvider.getMaxPage(Constants.ITEM_PER_PAGE, (int) ticketRepository.count()) ? 0 : evalPage;

        return ticketRepository.findAll(PageRequest.of(evalPage, Constants.ITEM_PER_PAGE));
    }
}