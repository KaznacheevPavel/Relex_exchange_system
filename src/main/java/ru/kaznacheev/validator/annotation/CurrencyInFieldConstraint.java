package ru.kaznacheev.validator.annotation;

import ru.kaznacheev.validator.CurrencyInFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CurrencyInFieldValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrencyInFieldConstraint {
    String message() default "Ошибка валюты";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
