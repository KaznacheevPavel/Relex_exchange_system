package ru.kaznacheev.service;

import ru.kaznacheev.dto.response.ExchangeResultDto;
import ru.kaznacheev.entity.Wallet;

import java.math.BigDecimal;

public interface WalletOperationService {

    Wallet deposit(String secretKey, String currency, BigDecimal amount);
    Wallet withdraw(String secretKey, String currency, BigDecimal amount, String transferTarget);
    ExchangeResultDto exchangeCurrency(String secretKey, String currencyFrom, String currencyTo, BigDecimal amount);

}
