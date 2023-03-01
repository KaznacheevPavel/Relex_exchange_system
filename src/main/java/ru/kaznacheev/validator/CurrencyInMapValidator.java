package ru.kaznacheev.validator;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.kaznacheev.entity.Currency;
import ru.kaznacheev.service.SupportiveService;
import ru.kaznacheev.validator.annotation.CurrencyInMapConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@Log4j2
public class CurrencyInMapValidator implements ConstraintValidator<CurrencyInMapConstraint, Map<String, BigDecimal>> {

    private SupportiveService supportiveService;

    @Override
    public void initialize(CurrencyInMapConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Map<String, BigDecimal> currenciesInfo, ConstraintValidatorContext constraintValidatorContext) {
        String currencyTitle;
        BigDecimal amount;
        boolean result = true;
        for (Map.Entry<String, BigDecimal> currencyInfo : currenciesInfo.entrySet()) {
            currencyTitle = currencyInfo.getKey();
            if (currencyTitle.contains("_wallet")) {
                currencyTitle = currencyTitle.replace("_wallet", "");
            }
            amount = currencyInfo.getValue().stripTrailingZeros();
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
        }
        return result;
    }

}
