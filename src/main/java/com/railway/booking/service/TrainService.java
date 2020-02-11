package com.railway.booking.service;

import com.railway.booking.model.Train;

import java.util.List;

public interface TrainService {

    Train getById(Integer id);

    List<Train> findAll(int pageNumber);

}
