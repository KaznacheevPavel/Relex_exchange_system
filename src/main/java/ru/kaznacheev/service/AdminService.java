package ru.kaznacheev.service;

import ru.kaznacheev.entity.Rate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AdminService {

    List<Rate> changeRate(String baseCurrency, Map<String, BigDecimal> currenciesForChange);
    BigDecimal getTotalAmountByCurrency(String currencyTitle);
    BigDecimal getOperationsCount(LocalDate dateFrom, LocalDate dateTo);

}
