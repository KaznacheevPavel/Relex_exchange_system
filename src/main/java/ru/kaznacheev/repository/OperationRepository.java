package ru.kaznacheev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.entity.Operation;

import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {

    Optional<Operation> findByTitle(String title);

}
