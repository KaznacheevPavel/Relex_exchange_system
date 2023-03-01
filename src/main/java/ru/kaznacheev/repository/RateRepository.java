package ru.kaznacheev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.entity.Currency;
import ru.kaznacheev.entity.Rate;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {

    List<Rate> findAllByCurrencyTo(Currency currency);
    Optional<Rate> findByCurrencyToAndCurrencyFrom(Currency currencyTo, Currency currencyFrom);

}
