package com.railway.booking.service.impl;

import com.railway.booking.model.CarriageType;
import com.railway.booking.model.Tariff;
import com.railway.booking.repository.TariffRepository;
import com.railway.booking.service.TariffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class TariffServiceImpl implements TariffService {
    private static final Logger LOGGER = LogManager.getLogger(TariffServiceImpl.class);

    private static final Integer MAX_PER_PAGE = 20;

    private final TariffRepository tariffRepository;

    @Autowired
    public TariffServiceImpl(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    @Override
    @Transactional
    public void update(Tariff tariff) {
        Tariff newTariff = tariffRepository.getTariffByCarriageType(tariff.getCarriageType()).orElse(null);
        newTariff.setRate(tariff.getRate());
        tariffRepository.save(tariff);
    }

    @Override
    public BigDecimal getRate(CarriageType carriageType) {
        Tariff tariff = tariffRepository.getTariffByCarriageType(carriageType).orElse(null);
        return tariff.getRate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tariff> findAll() {
        return tariffRepository.findAll();
    }
}