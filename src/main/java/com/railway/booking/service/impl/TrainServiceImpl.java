package com.railway.booking.service.impl;

import com.railway.booking.model.Train;
import com.railway.booking.repository.TrainRepository;
import com.railway.booking.repository.domain.Page;
import com.railway.booking.service.TrainService;
import com.railway.booking.service.validator.TrainValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TrainServiceImpl implements TrainService {
    private static final Logger LOGGER = LogManager.getLogger(TrainServiceImpl.class);

    private static final Integer TRAIN_PER_PAGE = 5;
    private final TrainValidator trainValidator;
    private final TrainRepository trainRepository;

    @Autowired
    public TrainServiceImpl(TrainRepository trainRepository, TrainValidator trainValidator) {
        this.trainRepository = trainRepository;
        this.trainValidator = trainValidator;
    }

    @Override
    @Transactional(readOnly = true)
    public Train getById(Integer id) {
        trainValidator.validateId(id);
        return trainRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Train> findAll(int pageNumber) {
        int maxPage = getMaxPage();
        if (pageNumber <= 0) {
            pageNumber = 1;
        } else if (pageNumber >= maxPage) {
            pageNumber = maxPage;
        }
        new Page(pageNumber, TRAIN_PER_PAGE);
        return trainRepository.findAll();
    }

    private int getMaxPage() {
        int totalUsers = (int) trainRepository.count();
        int page = totalUsers / TRAIN_PER_PAGE;
        if (totalUsers % TRAIN_PER_PAGE != 0) {
            page++;
        }
        return page == 0 ? 1 : page;
    }
}