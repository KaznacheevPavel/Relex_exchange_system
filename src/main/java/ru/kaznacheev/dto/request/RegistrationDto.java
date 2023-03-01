package ru.kaznacheev.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.kaznacheev.validator.annotation.RegistrationConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@RegistrationConstraint
public class RegistrationDto {

    @NotBlank(message = "Логин обязателен")
    private String username;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Ошибка в формате email")
    private String email;

}

