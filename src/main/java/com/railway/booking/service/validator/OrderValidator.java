package com.railway.booking.service.validator;

import com.railway.booking.entity.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class OrderValidator implements Validator<Order> {

    @Override
    public boolean isValid(Order entity) {
        boolean isValid = false;
        try {
            validateId(entity.getId());
            isValid = true;
        } catch (ValidateException e) {
            log.warn("Order entity validation exception occurred");
        }
        return isValid;
    }

    @Override
    public void validateId(Integer id) {
        if (id == null || id < 0) {
            String message = String.format("Provided order id for query: %d is not valid," +
                    "id couldn't be null or negative", id);
            log.warn(message);
            throw new ValidateException(message);
        }
    }
}
