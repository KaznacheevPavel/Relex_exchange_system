package ru.kaznacheev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDto {

    private Timestamp timestamp;
    private String Error;

}
