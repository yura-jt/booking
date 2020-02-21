package com.railway.booking.service;

import com.railway.booking.entity.Train;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TrainService {

    Optional<Train> getById(Integer id);

    Page<Train> findAll(String pageNumber);

}
