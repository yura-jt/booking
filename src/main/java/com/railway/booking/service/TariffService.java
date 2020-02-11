package com.railway.booking.service;

import com.railway.booking.model.CarriageType;
import com.railway.booking.model.Tariff;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

public interface TariffService {

    void update(Tariff tariff);

    BigDecimal getRate(CarriageType carriageType);

    List<Tariff> findAll();
}