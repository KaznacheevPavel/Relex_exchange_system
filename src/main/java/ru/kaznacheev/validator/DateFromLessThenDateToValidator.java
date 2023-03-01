package ru.kaznacheev.validator;

import ru.kaznacheev.dto.request.CountOperationsDto;
import ru.kaznacheev.validator.annotation.DateFromLessThenDateToConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateFromLessThenDateToValidator implements ConstraintValidator<DateFromLessThenDateToConstraint, CountOperationsDto> {

    @Override
    public void initialize(DateFromLessThenDateToConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CountOperationsDto countOperationsDto, ConstraintValidatorContext constraintValidatorContext) {
        if (countOperationsDto.getDateFrom().isAfter(countOperationsDto.getDateTo())) {
            return false;
        }
        return true;
    }

}
