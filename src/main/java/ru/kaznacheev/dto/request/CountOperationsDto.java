package ru.kaznacheev.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.validator.annotation.DateFromLessThenDateToConstraint;
import ru.kaznacheev.validator.annotation.DateLessThenNowConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DateFromLessThenDateToConstraint(message = "Дата начала выбора не может быть позже чем дата окончания")
public class CountOperationsDto {

    @JsonProperty("secret_key")
    @NotBlank(message = "Секретный ключ обязателен")
    private String secretKey;

    @JsonProperty("date_from")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd.MM.yyyy")
    @DateLessThenNowConstraint(message = "Неверная дата начала выбора")
    @NotNull(message = "Дата начала выбора обязательна")
    private LocalDate dateFrom;

    @JsonProperty("date_to")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd.MM.yyyy")
    @NotNull(message = "Дата окончания выбора обязательна")
    private LocalDate dateTo;

}
