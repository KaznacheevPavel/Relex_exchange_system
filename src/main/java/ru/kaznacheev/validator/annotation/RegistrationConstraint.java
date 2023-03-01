package ru.kaznacheev.validator.annotation;

import ru.kaznacheev.validator.RegistrationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RegistrationValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegistrationConstraint {
    String message() default "Ошибка регистрации";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
