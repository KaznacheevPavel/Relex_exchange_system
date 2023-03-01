package ru.kaznacheev.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.kaznacheev.validator.annotation.CurrencyInFieldConstraint;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@CurrencyInFieldConstraint
public class WithdrawDto {

    @JsonProperty("secret_key")
    @NotBlank(message = "Секретный ключ обязателен")
    private String secretKey;

    @NotBlank(message = "Валюта обязательна")
    private String currency;

    @NotNull(message = "Сумма для вывода обязательна")
    private BigDecimal amount;

    @JsonProperty("credit_card")
    @Pattern(regexp = "^\\d{4}_\\d{4}_\\d{4}_\\d{4}$", message = "Неверный формат карты")
    private String creditCard;

    private String wallet;

}
