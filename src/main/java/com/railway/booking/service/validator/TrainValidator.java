package com.railway.booking.service.validator;

import com.railway.booking.entity.Train;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Log4j2
@Component
public class TrainValidator implements Validator<Train> {
    private static final Integer MAX_CODE_LENGTH = 10;
    private static final Integer MAX_NAME_LENGTH = 100;

    @Override
    public boolean isValid(Train entity) {
        boolean isValid = false;
        try {
            validateStringLength(entity.getCode(), MAX_CODE_LENGTH);
            validateStringLength(entity.getName(), MAX_NAME_LENGTH);
            isValid = true;
        } catch (ValidateException e) {
            log.warn("Train entity validation exception occurred");
        }
        return isValid;
    }

    @Override
    public void validateId(Integer id) {
        if (id == null || id < 0) {
            String message = String.format("Provided user id for query: %d is not valid," +
                    "id couldn't be null or negative", id);
            log.warn(message);
            throw new ValidateException(message);
        }
    }

    public void validateDate(LocalDate localDate) {
        if (localDate == null || localDate.isBefore(LocalDate.now())) {
            String message = String.format("Local date: %s for train search is expired", localDate.toString());
            log.warn(message);
            throw new ValidateException(message);
        }
    }

    private void validateStringLength(String field, Integer upperBound) {
        if (field == null || field.length() >= upperBound) {
            String message = String.format("Train %s length is out of bound. " +
                    "Maximum allowed length is: %s", field, upperBound);
            log.warn(message);
            throw new ValidateException(message);
        }
    }
}
