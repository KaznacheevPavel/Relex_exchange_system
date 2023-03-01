package ru.kaznacheev.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kaznacheev.dto.request.SecretKeyDto;
import ru.kaznacheev.dto.response.ExchangeResultDto;
import ru.kaznacheev.dto.request.CurrencyDto;
import ru.kaznacheev.dto.request.DepositDto;
import ru.kaznacheev.dto.request.ExchangeCurrencyDto;
import ru.kaznacheev.dto.request.WithdrawDto;
import ru.kaznacheev.dto.response.RateInfoDto;
import ru.kaznacheev.dto.response.WalletInfoDto;
import ru.kaznacheev.entity.Client;
import ru.kaznacheev.entity.Rate;
import ru.kaznacheev.entity.Wallet;
import ru.kaznacheev.service.SupportiveService;
import ru.kaznacheev.service.WalletOperationService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Validated
@AllArgsConstructor
@Log4j2
public class OperationController {

    private WalletOperationService walletOperationService;
    private SupportiveService supportiveService;

    @GetMapping(value = "/wallets", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public WalletInfoDto getBalance(@Valid @RequestBody SecretKeyDto secretKeyDto) {
        Client client = supportiveService.getClient(secretKeyDto.getSecretKey());
        return new WalletInfoDto(client.getWallets());
    }

    @PostMapping(value = "/wallets", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public WalletInfoDto deposit(@Valid @RequestBody DepositDto depositDto) {
        String secretKey = depositDto.getSecretKey();
        String currencyTitle = depositDto.getCurrency().keySet().toArray()[0].toString();
        BigDecimal amount = depositDto.getCurrency().get(currencyTitle);
        Wallet wallet = walletOperationService.deposit(secretKey, currencyTitle.replace("_wallet", ""), amount);
        return new WalletInfoDto(wallet);
    }

    @PostMapping(value = "/wallets/withdraw", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public WalletInfoDto withdraw(@Valid @RequestBody WithdrawDto withdrawDto) {
        String secretKey = withdrawDto.getSecretKey();
        String currencyTitle = withdrawDto.getCurrency();
        BigDecimal amount = withdrawDto.getAmount();
        String transferTarget = "";
        if (withdrawDto.getWallet() == null) {
            transferTarget = withdrawDto.getCreditCard();
        } else {
            transferTarget = withdrawDto.getWallet();
        }
        Wallet wallet = walletOperationService.withdraw(secretKey, currencyTitle, amount, transferTarget);
        return new WalletInfoDto(wallet);
    }

    @PostMapping(value = "/wallets/exchange", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ExchangeResultDto exchangeCurrency(@Valid @RequestBody ExchangeCurrencyDto exchangeCurrencyDto) {
        String secretKey = exchangeCurrencyDto.getSecretKey();
        String currencyFrom = exchangeCurrencyDto.getCurrencyFrom();
        String currencyTo = exchangeCurrencyDto.getCurrencyTo();
        BigDecimal amount = exchangeCurrencyDto.getAmount();
        return walletOperationService.exchangeCurrency(secretKey, currencyFrom, currencyTo, amount);
    }

    @GetMapping(value = "/rates", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RateInfoDto getRates(@Valid @RequestBody CurrencyDto currencyDto) {
        List<Rate> rates = supportiveService.getRates(currencyDto.getCurrency());
        return new RateInfoDto(rates);
    }

}
