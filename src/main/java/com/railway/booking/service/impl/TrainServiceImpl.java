package com.railway.booking.service.impl;

import com.railway.booking.entity.Train;
import com.railway.booking.repository.TrainRepository;
import com.railway.booking.service.PageProvider;
import com.railway.booking.service.TrainService;
import com.railway.booking.service.validator.TrainValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TrainServiceImpl implements TrainService {
    private static final Integer TRAIN_PER_PAGE = 5;

    private final TrainValidator trainValidator;
    private final TrainRepository trainRepository;
    private final PageProvider pageProvider;

    @Autowired
    public TrainServiceImpl(TrainRepository trainRepository,
                            TrainValidator trainValidator, PageProvider pageProvider) {
        this.trainRepository = trainRepository;
        this.trainValidator = trainValidator;
        this.pageProvider = pageProvider;
    }

    @Override
    @Transactional(readOnly = true)
    public Train getById(Integer id) {
        trainValidator.validateId(id);
        return trainRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Train> findAll(String page) {
        int currentPage = pageProvider.getPageNumberFromString(page);

        int evalPage = (currentPage < 1) ? 1 : (currentPage - 1);
        evalPage = evalPage > pageProvider.getMaxPage(TRAIN_PER_PAGE, (int) trainRepository.count()) ? 0 : evalPage;

        return trainRepository.findAll(PageRequest.of(evalPage, TRAIN_PER_PAGE));
    }
}