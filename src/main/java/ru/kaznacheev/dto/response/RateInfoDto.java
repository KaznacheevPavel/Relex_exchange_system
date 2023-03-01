package ru.kaznacheev.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.entity.Rate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "Rate_information")
public class RateInfoDto {

    @JsonValue
    private Map<String, String> ratesInfo;

    public RateInfoDto(List<Rate> rates) {
        ratesInfo = new HashMap<>();
        for (Rate rate : rates) {
            ratesInfo.put(rate.getCurrencyFrom().getTitle(), rate.getCost().stripTrailingZeros().toString());
        }
    }

}
