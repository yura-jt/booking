package com.railway.booking.service;

import com.railway.booking.entity.Train;
import org.springframework.data.domain.Page;

public interface TrainService {

    Train getById(Integer id);

    Page<Train> findAll(String pageNumber);

}
