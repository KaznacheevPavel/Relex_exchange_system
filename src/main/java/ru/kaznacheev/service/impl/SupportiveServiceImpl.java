package ru.kaznacheev.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.entity.*;
import ru.kaznacheev.repository.*;
import ru.kaznacheev.service.SupportiveService;
import ru.kaznacheev.exception.ErrorException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class SupportiveServiceImpl implements SupportiveService {

    private CurrencyRepository currencyRepository;
    private RateRepository rateRepository;
    private WalletRepository walletRepository;
    private ClientRepository clientRepository;
    private OperationRepository operationRepository;
    private RoleRepository roleRepository;

    @Override
    public List<Currency> getCurrencies() {
        List<Currency> currencies = currencyRepository.findAll();
        if (currencies.isEmpty()) {
            throw new ErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Не удалось получить список валют");
        }
        return currencies;
    }

    @Override
    public Currency getCurrency(String title) {
        Optional<Currency> currency = currencyRepository.findByTitle(title);
        if (currency.isEmpty()) {
            throw new ErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Не удалось получить указанную валюту");
        }
        return currency.get();
    }

    @Override
    public Wallet getWallet(Client client, String currencyTitle) {
        Currency currency = getCurrency(currencyTitle);
        Optional<Wallet> wallet = walletRepository.findByClientAndCurrency(client, currency);
        if (wallet.isPresent()) {
            return wallet.get();
        }
        throw new ErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Не удалось получить кошелек");
    }

    @Override
    public List<Rate> getRates(String currencyTitle) {
        Currency currency = getCurrency(currencyTitle);
        List<Rate> rates = rateRepository.findAllByCurrencyTo(currency);
        if (rates.isEmpty()) {
            throw new ErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Не удалось получить список курсов валют");
        }
        return rates;
    }

    @Override
    public Rate getRate(String currencyToTitle, String currencyFromTitle) {
        Currency currencyTo = getCurrency(currencyToTitle);
        Currency currencyFrom = getCurrency(currencyFromTitle);
        Optional<Rate> rate = rateRepository.findByCurrencyToAndCurrencyFrom(currencyTo, currencyFrom);
        if (rate.isEmpty()) {
            throw new ErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Не удалось получить курс валюты");
        }
        return rate.get();
    }

    @Override
    public Client getClient(String secretKey) {
        Optional<Client> client = clientRepository.findBySecretKey(secretKey);
        if (client.isEmpty()) {
            throw new ErrorException(HttpStatus.BAD_REQUEST, "Неверный секретный ключ");
        }
        return client.get();
    }

    @Override
    public Operation getOperation(String title) {
        Optional<Operation> operation = operationRepository.findByTitle(title);
        if (operation.isEmpty()) {
            throw new ErrorException(HttpStatus.BAD_REQUEST, "Не удалось получить название операции");
        }
        return operation.get();
    }

    @Override
    public Role getRole(String title) {
        Optional<Role> role = roleRepository.findByTitle(title);
        if (role.isPresent()) {
            return role.get();
        }
        throw new ErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Не удалось получить роль");
    }

}
