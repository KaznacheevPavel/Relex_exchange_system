package ru.kaznacheev.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Rate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rate {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "currency_to", referencedColumnName = "id")
    private Currency currencyTo;

    @ManyToOne
    @JoinColumn(name = "currency_from", referencedColumnName = "id")
    private Currency currencyFrom;

    @Column(name = "cost")
    private BigDecimal cost;

}
