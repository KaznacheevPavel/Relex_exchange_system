package ru.kaznacheev.validator;

import lombok.AllArgsConstructor;
import ru.kaznacheev.dto.request.WithdrawDto;
import ru.kaznacheev.entity.Currency;
import ru.kaznacheev.service.SupportiveService;
import ru.kaznacheev.validator.annotation.CurrencyInFieldConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

@AllArgsConstructor
public class CurrencyInFieldValidator implements ConstraintValidator<CurrencyInFieldConstraint, WithdrawDto> {

    private SupportiveService supportiveService;

    @Override
    public void initialize(CurrencyInFieldConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(WithdrawDto withdrawDto, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;
        String currencyTitle = withdrawDto.getCurrency();
        BigDecimal amount = withdrawDto.getAmount().stripTrailingZeros();
        Currency currency = supportiveService.getCurrency(currencyTitle);
        BigDecimal minimalAmount = currency.getMinimalAmount().stripTrailingZeros();
        if (amount.scale() > minimalAmount.scale()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Максимальное количество знаков после запятой для этой валюты равно " + minimalAmount.scale()).addConstraintViolation();
            result = false;
        }
        if (minimalAmount.compareTo(amount) > 0) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Минимальная сумма в этой валюте равна " + minimalAmount.stripTrailingZeros()).addConstraintViolation();
            result = false;
        }
        return result;
    }

}
