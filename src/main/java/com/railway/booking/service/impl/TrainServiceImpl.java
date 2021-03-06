package com.railway.booking.service.impl;

import com.railway.booking.entity.Train;
import com.railway.booking.repository.TrainRepository;
import com.railway.booking.service.PageProvider;
import com.railway.booking.service.TrainService;
import com.railway.booking.service.util.Constants;
import com.railway.booking.service.validator.TrainValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {
    private final TrainValidator trainValidator;
    private final TrainRepository trainRepository;
    private final PageProvider pageProvider;

    @Override
    @Transactional(readOnly = true)
    public Optional<Train> getById(Integer id) {
        trainValidator.validateId(id);
        return trainRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Train> findAll(String page) {
        int currentPage = pageProvider.getPageNumberFromString(page);

        int evalPage = (currentPage < 1) ? 1 : (currentPage - 1);
        evalPage = evalPage > pageProvider.getMaxPage(Constants.ITEM_PER_PAGE, (int) trainRepository.count()) ? 0 : evalPage;

        return trainRepository.findAll(PageRequest.of(evalPage, Constants.ITEM_PER_PAGE));
    }
}