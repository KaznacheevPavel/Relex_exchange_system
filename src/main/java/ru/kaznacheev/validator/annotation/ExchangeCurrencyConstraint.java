package ru.kaznacheev.validator.annotation;

import ru.kaznacheev.validator.ExchangeCurrencyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExchangeCurrencyValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExchangeCurrencyConstraint {
    String message() default "Ошибка валюты";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
