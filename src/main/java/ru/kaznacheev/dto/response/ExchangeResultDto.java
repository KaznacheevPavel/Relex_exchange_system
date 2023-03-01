package ru.kaznacheev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeResultDto {

    private String currencyFrom;
    private String currencyTo;
    private String amountFrom;
    private String amountTo;

}
