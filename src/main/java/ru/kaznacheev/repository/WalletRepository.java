package ru.kaznacheev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.entity.Client;
import ru.kaznacheev.entity.Currency;
import ru.kaznacheev.entity.Wallet;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    Optional<Wallet> findByClientAndCurrency(Client client, Currency currency);

    @Query("SELECT SUM(w.amount) FROM Wallet w WHERE w.currency = ?1")
    Optional<BigDecimal> getTotalAmountByCurrency(Currency currency);

}
