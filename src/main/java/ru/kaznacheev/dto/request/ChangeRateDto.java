package ru.kaznacheev.dto.request;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.validator.annotation.CurrencyInMapConstraint;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRateDto {

    @JsonProperty("secret_key")
    @NotBlank(message = "Секретный ключ обязателен")
    private String secretKey;

    @JsonProperty("base_currency")
    @NotBlank(message = "Базовая валюта обязательна")
    private String baseCurrency;

    @JsonAnySetter
    @CurrencyInMapConstraint
    private Map<String, BigDecimal> currencies = new HashMap<>();

}
