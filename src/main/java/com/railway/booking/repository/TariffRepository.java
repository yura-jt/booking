package com.railway.booking.repository;

import com.railway.booking.model.CarriageType;
import com.railway.booking.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Integer> {

    Optional<Tariff> getTariffByCarriageType(CarriageType carriageType);
}
