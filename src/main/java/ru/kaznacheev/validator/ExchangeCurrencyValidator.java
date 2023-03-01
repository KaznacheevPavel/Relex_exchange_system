package ru.kaznacheev.validator;

import lombok.AllArgsConstructor;
import ru.kaznacheev.dto.request.ExchangeCurrencyDto;
import ru.kaznacheev.entity.Currency;
import ru.kaznacheev.service.SupportiveService;
import ru.kaznacheev.validator.annotation.ExchangeCurrencyConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

@AllArgsConstructor
public class ExchangeCurrencyValidator implements ConstraintValidator<ExchangeCurrencyConstraint, ExchangeCurrencyDto> {

    private SupportiveService supportiveService;

    @Override
    public void initialize(ExchangeCurrencyConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ExchangeCurrencyDto exchangeCurrencyDto, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;
        String currencyTitle = exchangeCurrencyDto.getCurrencyFrom();
        BigDecimal amount = exchangeCurrencyDto.getAmount().stripTrailingZeros();
        Currency currency = supportiveService.getCurrency(currencyTitle);
        BigDecimal minimalAmount = currency.getMinimalAmount().stripTrailingZeros();
        if (amount.scale() > minimalAmount.scale()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Максимальное количество знаков после запятой для " + currencyTitle + " равно " + minimalAmount.scale()).addConstraintViolation();
            result = false;
        }
        if (minimalAmount.compareTo(amount) > 0) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Минимальная сумма в " + currencyTitle + " равна " + minimalAmount.stripTrailingZeros()).addConstraintViolation();
            result = false;
        }
        return result;
    }

}
