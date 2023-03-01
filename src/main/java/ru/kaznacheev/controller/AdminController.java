package ru.kaznacheev.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kaznacheev.dto.request.ChangeRateDto;
import ru.kaznacheev.dto.request.CountOperationsDto;
import ru.kaznacheev.dto.request.CurrencyDto;
import ru.kaznacheev.dto.response.RateInfoDto;
import ru.kaznacheev.dto.response.StatisticDto;
import ru.kaznacheev.entity.Rate;
import ru.kaznacheev.service.AdminService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Validated
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;

    @PostMapping(value = "rates", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RateInfoDto changeRate(@Valid @RequestBody ChangeRateDto changeRateDto) {
        List<Rate> rates = adminService.changeRate(changeRateDto.getBaseCurrency(), changeRateDto.getCurrencies());
        return new RateInfoDto(rates);
    }

    @GetMapping(value = "/stats/amount_currency", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public StatisticDto getTotalAmount(@Valid @RequestBody CurrencyDto currencyDto) {
        BigDecimal result = adminService.getTotalAmountByCurrency(currencyDto.getCurrency());
        return new StatisticDto(currencyDto.getCurrency(), result.stripTrailingZeros().toPlainString());
    }

    @GetMapping(value = "/stats/operations_count", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public StatisticDto getOperationsCount(@Valid @RequestBody CountOperationsDto countOperationsDto) {
        BigDecimal result = adminService.getOperationsCount(countOperationsDto.getDateFrom(), countOperationsDto.getDateTo());
        return new StatisticDto("transaction_count", result.stripTrailingZeros().toPlainString());
    }

}
