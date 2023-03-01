package ru.kaznacheev.service;

import ru.kaznacheev.entity.*;

import java.util.List;

public interface SupportiveService {

    List<Currency> getCurrencies();
    Currency getCurrency(String title);
    Wallet getWallet(Client client, String currencyTitle);
    List<Rate> getRates(String currencyTitle);
    Rate getRate(String currencyTo, String currencyFrom);
    Client getClient(String secretKey);
    Operation getOperation(String title);

    Role getRole(String title);

}
