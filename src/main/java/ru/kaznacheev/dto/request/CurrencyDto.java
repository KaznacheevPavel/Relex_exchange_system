package ru.kaznacheev.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {

    @JsonProperty("secret_key")
    @NotBlank(message = "Секретный ключ обязателен")
    private String secretKey;

    @NotBlank(message = "Валюта обязательна")
    private String currency;

}
