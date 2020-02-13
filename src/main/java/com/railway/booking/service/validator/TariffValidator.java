package com.railway.booking.service.validator;

import com.railway.booking.entity.Tariff;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Log4j2
@Component
public class TariffValidator implements Validator<Tariff> {

    @Override
    public boolean isValid(Tariff entity) {
        boolean isValid = false;
        try {
            validateId(entity.getId());
            validateRate(entity.getRate());
            isValid = true;
        } catch (ValidateException e) {
            log.warn("Tariff entity validation exception occurred");
        }
        return isValid;
    }

    @Override
    public void validateId(Integer id) {
        if (id == null || id < 0) {
            String message = String.format("Provided tariff id: %d is not correct," +
                    "id couldn't be null or negative", id);
            log.warn(message);
            throw new ValidateException(message);
        }
    }

    private void validateRate(BigDecimal rate) {
        if (rate == null || rate.compareTo(BigDecimal.ZERO) <= 0) {
            String message = ("Provided tariff rate: is not correct," +
                    "rate couldn't be null or negative");
            log.warn(message);
            throw new ValidateException(message);
        }
    }
}
