package ru.kaznacheev.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "Statistic")
public class StatisticDto {

    @JsonValue
    private Map<String, String> statistic;

    public StatisticDto(String title, String value) {
        statistic = new HashMap<>();
        statistic.put(title, value);
    }

}
