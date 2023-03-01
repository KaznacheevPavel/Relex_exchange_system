package ru.kaznacheev.validator.annotation;

import ru.kaznacheev.validator.DateFromLessThenDateToValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateFromLessThenDateToValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFromLessThenDateToConstraint {
    String message() default "Одна дата не может быть больше другой";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
