package ru.kaznacheev.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.entity.Currency;
import ru.kaznacheev.entity.Rate;
import ru.kaznacheev.repository.OperationHistoryRepository;
import ru.kaznacheev.repository.WalletRepository;
import ru.kaznacheev.service.AdminService;
import ru.kaznacheev.service.SupportiveService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private WalletRepository walletRepository;
    private OperationHistoryRepository operationHistoryRepository;
    private SupportiveService supportiveService;

    @Transactional
    @Override
    public List<Rate> changeRate(String baseCurrency, Map<String, BigDecimal> currenciesForChange) {
        for (Map.Entry<String, BigDecimal> currencyFrom : currenciesForChange.entrySet()) {
            Rate rate = supportiveService.getRate(baseCurrency, currencyFrom.getKey());
            rate.setCost(currencyFrom.getValue());
        }
        return supportiveService.getRates(baseCurrency);
    }

    @Transactional(readOnly = true)
    @Override
    public BigDecimal getTotalAmountByCurrency(String currencyTitle) {
        Currency currency = supportiveService.getCurrency(currencyTitle);
        Optional<BigDecimal> totalAmount = walletRepository.getTotalAmountByCurrency(currency);
        if (totalAmount.isEmpty()) {
            return new BigDecimal(0);
        }
        return totalAmount.get();
    }

    @Transactional(readOnly = true)
    @Override
    public BigDecimal getOperationsCount(LocalDate dateFrom, LocalDate dateTo) {
        Optional<BigDecimal> operationsCount = operationHistoryRepository.countAllByDateBetween(dateFrom, dateTo);
        if (operationsCount.isEmpty()) {
            return new BigDecimal(0);
        }
        return operationsCount.get();
    }

}
