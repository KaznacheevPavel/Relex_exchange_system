package ru.kaznacheev.validator;

import ru.kaznacheev.validator.annotation.DateLessThenNowConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateLessThenNowValidator implements ConstraintValidator<DateLessThenNowConstraint, LocalDate> {

    @Override
    public void initialize(DateLessThenNowConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if (date.isAfter(LocalDate.now())) {
            return false;
        }
        return true;
    }

}
