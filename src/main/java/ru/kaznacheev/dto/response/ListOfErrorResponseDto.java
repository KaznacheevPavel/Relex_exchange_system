package ru.kaznacheev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ListOfErrorResponseDto {

    private Timestamp timestamp;
    private List<String> errors;

}
