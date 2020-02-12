package com.railway.booking.service;

import com.railway.booking.model.Train;
import org.springframework.data.domain.Page;

public interface TrainService {

    Train getById(Integer id);

    Page<Train> findAll(String pageNumber);

}
