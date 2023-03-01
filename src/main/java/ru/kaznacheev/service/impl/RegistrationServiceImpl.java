package ru.kaznacheev.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.entity.Client;
import ru.kaznacheev.entity.Currency;
import ru.kaznacheev.entity.Wallet;
import ru.kaznacheev.repository.ClientRepository;
import ru.kaznacheev.repository.WalletRepository;
import ru.kaznacheev.service.EmailService;
import ru.kaznacheev.service.RegistrationService;
import ru.kaznacheev.service.SupportiveService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private ClientRepository clientRepository;
    private WalletRepository walletRepository;
    private SupportiveService supportiveService;
    private EmailService emailService;

    @Transactional
    @Override
    public String registration(Client client) {
        String secretKey = generateSecretKey(client.getUsername());
        client.setSecretKey(secretKey);
        client.setRole(supportiveService.getRole("INACTIVE_USER"));
        clientRepository.save(client);
        emailService.sendEmail(client.getEmail(), secretKey);
        return secretKey;
    }

    @Transactional
    @Override
    public String activate(Client client) {
        if (!client.getRole().equals(supportiveService.getRole("INACTIVE_USER"))) {
            return "Аккаунт уже активирован";
        }
        client.setRole(supportiveService.getRole("USER"));
        clientRepository.save(client);
        createWallets(client);
        return "Аккаунт успешно активирован";
    }

    private void createWallets(Client client) {
        List<Currency> currencies = supportiveService.getCurrencies();
        List<Wallet> wallets = new ArrayList<>();
        for (Currency currency : currencies) {
            Wallet wallet = new Wallet();
            wallet.setClient(client);
            wallet.setCurrency(currency);
            wallet.setAmount(new BigDecimal(0));
            wallets.add(wallet);
        }
        walletRepository.saveAll(wallets);
    }

    private String generateSecretKey(String username) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(username);
    }

}
