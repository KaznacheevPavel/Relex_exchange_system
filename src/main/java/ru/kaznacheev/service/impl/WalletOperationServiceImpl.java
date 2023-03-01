package ru.kaznacheev.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.dto.response.ExchangeResultDto;
import ru.kaznacheev.entity.*;
import ru.kaznacheev.exception.ErrorException;
import ru.kaznacheev.repository.OperationHistoryRepository;
import ru.kaznacheev.repository.WalletRepository;
import ru.kaznacheev.service.SupportiveService;
import ru.kaznacheev.service.WalletOperationService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@AllArgsConstructor
@Log4j2
public class WalletOperationServiceImpl implements WalletOperationService {

    private WalletRepository walletRepository;
    private OperationHistoryRepository operationHistoryRepository;
    private SupportiveService supportiveService;

    @Transactional
    @Override
    public Wallet deposit(String secretKey, String currency, BigDecimal amount) {
        Client client = supportiveService.getClient(secretKey);
        Wallet wallet = supportiveService.getWallet(client, currency);
        BigDecimal newAmount = wallet.getAmount().add(amount);

        wallet.setAmount(newAmount);
        walletRepository.save(wallet);

        Operation operation = supportiveService.getOperation("Пополнение");
        OperationHistory operationHistory = new OperationHistory(operation, client, wallet.getCurrency(), LocalDate.now(), amount);
        operationHistoryRepository.save(operationHistory);
        return wallet;
    }

    @Transactional
    @Override
    public Wallet withdraw(String secretKey, String currency, BigDecimal amount, String transferWallet) {
        Client client = supportiveService.getClient(secretKey);
        Wallet wallet = supportiveService.getWallet(client, currency);
        if (wallet.getAmount().compareTo(amount) < 0) {
            throw new ErrorException(HttpStatus.BAD_REQUEST, "Недостаточно валюты для вывода");
        }

        BigDecimal newAmount = wallet.getAmount().subtract(amount);
        wallet.setAmount(newAmount);
        walletRepository.save(wallet);

        Operation operation = supportiveService.getOperation("Вывод");
        OperationHistory operationHistory = new OperationHistory(operation, client, wallet.getCurrency(), LocalDate.now(), amount, transferWallet);
        operationHistoryRepository.save(operationHistory);
        return wallet;
    }

    @Transactional
    @Override
    public ExchangeResultDto exchangeCurrency(String secretKey, String currencyFrom, String currencyTo, BigDecimal amount) {
        Client client = supportiveService.getClient(secretKey);
        Wallet walletFrom = supportiveService.getWallet(client, currencyFrom);
        Wallet walletTo = supportiveService.getWallet(client, currencyTo);
        Rate rate = supportiveService.getRate(currencyTo, currencyFrom);
        if (walletFrom.getAmount().compareTo(amount) < 0) {
            throw new ErrorException(HttpStatus.BAD_REQUEST, "Недостаточно " + currencyFrom);
        }
        // Минимальное возможное количество покупаемой валюты
        BigDecimal minimumPurchasedCurrencyAmount = walletTo.getCurrency().getMinimalAmount().stripTrailingZeros();
        // Стоимость минимального количества покупаемой валюты
        BigDecimal amountNeedToMinimumPurchase = rate.getCost().stripTrailingZeros()
                .multiply(minimumPurchasedCurrencyAmount)
                .setScale(walletFrom.getCurrency().getMinimalAmount().stripTrailingZeros().scale(), RoundingMode.HALF_UP);
        if (walletFrom.getAmount().compareTo(amountNeedToMinimumPurchase) < 0) {
            throw new ErrorException(HttpStatus.BAD_REQUEST,
                    "Недостаточно "+ currencyFrom + " для покупки минимального количества " + currencyTo);
        }
        // Количество валюты, которое можем купить (Считается в minimumPurchasedCurrencyAmount)
        BigDecimal canBuyAmount = amount.divide(amountNeedToMinimumPurchase, minimumPurchasedCurrencyAmount.scale(), RoundingMode.DOWN);
        // Целое число от количества, которое можем купить
        BigDecimal canBuyAmountIntPart = canBuyAmount.setScale(0, RoundingMode.DOWN);
        // Сколько тратим
        BigDecimal spendAmount = amountNeedToMinimumPurchase.multiply(canBuyAmountIntPart);
        // Сколько получаем
        BigDecimal buyAmount = minimumPurchasedCurrencyAmount.multiply(canBuyAmountIntPart);
        walletFrom.setAmount(walletFrom.getAmount().subtract(spendAmount));
        walletTo.setAmount(walletTo.getAmount().add(buyAmount));

        ExchangeResultDto exchangeResultDto = new ExchangeResultDto();
        exchangeResultDto.setCurrencyTo(currencyTo);
        exchangeResultDto.setCurrencyFrom(currencyFrom);
        exchangeResultDto.setAmountFrom(spendAmount.stripTrailingZeros().toPlainString());
        exchangeResultDto.setAmountTo(buyAmount.stripTrailingZeros().toPlainString());

        Operation operation = supportiveService.getOperation("Обмен");
        OperationHistory operationHistory = new OperationHistory(operation, walletFrom.getClient(), walletFrom.getCurrency(), LocalDate.now(), amount, walletTo.getCurrency());
        operationHistoryRepository.save(operationHistory);
        return exchangeResultDto;
    }

}
