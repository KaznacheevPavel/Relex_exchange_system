package ru.kaznacheev.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Operation_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationHistory {

    public OperationHistory(Operation operation, Client user, Currency currency, LocalDate date, BigDecimal amount) {
        this.operation = operation;
        this.user = user;
        this.currency = currency;
        this.date = date;
        this.amount = amount;
    }

    public OperationHistory(Operation operation, Client user, Currency currency, LocalDate date, BigDecimal amount, Currency transferCurrency) {
        this.operation = operation;
        this.user = user;
        this.currency = currency;
        this.date = date;
        this.amount = amount;
        this.transferCurrency = transferCurrency;
    }

    public OperationHistory(Operation operation, Client user, Currency currency, LocalDate date, BigDecimal amount, String transferWallet) {
        this.operation = operation;
        this.user = user;
        this.currency = currency;
        this.date = date;
        this.amount = amount;
        this.transferWallet = transferWallet;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "operation_id", referencedColumnName = "id")
    private Operation operation;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client user;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "transfer_currency_id", referencedColumnName = "id")
    private Currency transferCurrency;

    @Column(name = "transfer_wallet")
    private String transferWallet;

}
