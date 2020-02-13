package com.railway.booking.service.validator;

import com.railway.booking.entity.Bill;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Log4j2
@Component
public class BillValidator implements Validator<Bill> {

    @Override
    public boolean isValid(Bill entity) {
        boolean isValid = false;
        try {
            validateId(entity.getOrder().getId());
            isValid = true;
        } catch (ValidateException e) {
            log.warn("Bill entity validation exception occurred");
        }
        return isValid;
    }

    private void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            String message = ("Provided price: is not correct," +
                    "price couldn't be null or negative");
            log.warn(message);
            throw new ValidateException(message);
        }
    }

    @Override
    public void validateId(Integer id) {
        if (id == null || id < 0) {
            String message = String.format("Provided Bill id for query: %d is not valid," +
                    "id couldn't be null or negative", id);
            log.warn(message);
            throw new ValidateException(message);
        }
    }
}