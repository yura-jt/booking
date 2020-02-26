package com.railway.booking.service.impl;

import com.railway.booking.entity.CarriageType;
import com.railway.booking.entity.Tariff;
import com.railway.booking.repository.TariffRepository;
import com.railway.booking.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {
    private final TariffRepository tariffRepository;

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