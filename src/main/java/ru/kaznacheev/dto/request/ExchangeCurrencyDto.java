package ru.kaznacheev.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.validator.annotation.ExchangeCurrencyConstraint;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ExchangeCurrencyConstraint
public class ExchangeCurrencyDto {

    @JsonProperty("secret_key")
    @NotBlank(message = "Секретный ключ обязателен")
    private String secretKey;

    @JsonProperty("currency_from")
    @NotBlank(message = "Базовая валюта обязательна")
    private String currencyFrom;

    @JsonProperty("currency_to")
    @NotBlank(message = "Валюта в которую осуществляется перевод обязательна")
    private String currencyTo;
    private BigDecimal amount;

}
