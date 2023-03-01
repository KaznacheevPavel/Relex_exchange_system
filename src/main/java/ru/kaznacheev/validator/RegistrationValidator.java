package ru.kaznacheev.validator;

import lombok.AllArgsConstructor;
import ru.kaznacheev.dto.request.RegistrationDto;
import ru.kaznacheev.entity.Client;
import ru.kaznacheev.repository.ClientRepository;
import ru.kaznacheev.validator.annotation.RegistrationConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@AllArgsConstructor
public class RegistrationValidator implements ConstraintValidator<RegistrationConstraint, RegistrationDto> {

    private ClientRepository userRepository;

    @Override
    public void initialize(RegistrationConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegistrationDto registrationDto, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;

        Optional<Client> user = userRepository.findByUsername(registrationDto.getUsername());
        if (user.isPresent()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Такое имя пользователя уже используется").addConstraintViolation();
            result = false;
        }

        user = userRepository.findByEmail(registrationDto.getEmail());
        if (user.isPresent()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Такой email уже используется").addConstraintViolation();
            result = false;
        }
        return result;
    }
}
