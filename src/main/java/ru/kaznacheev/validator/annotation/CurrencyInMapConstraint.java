package ru.kaznacheev.validator.annotation;


import ru.kaznacheev.validator.CurrencyInMapValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CurrencyInMapValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrencyInMapConstraint {
    String message() default "Ошибка валюты";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
