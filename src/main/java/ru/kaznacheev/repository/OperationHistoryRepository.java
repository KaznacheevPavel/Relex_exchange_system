package ru.kaznacheev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.entity.OperationHistory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface OperationHistoryRepository extends JpaRepository<OperationHistory, Integer> {

    Optional<BigDecimal> countAllByDateBetween(LocalDate dateFrom, LocalDate dateTo);

}
