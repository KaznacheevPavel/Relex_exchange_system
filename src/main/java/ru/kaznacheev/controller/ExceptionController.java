package ru.kaznacheev.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kaznacheev.exception.ErrorException;
import ru.kaznacheev.dto.response.ErrorResponseDto;
import ru.kaznacheev.dto.response.ListOfErrorResponseDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleException(ErrorException exception) {
        ErrorResponseDto response = new ErrorResponseDto(new Timestamp(System.currentTimeMillis()), exception.getError());
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }

    @ExceptionHandler
    private ResponseEntity<ListOfErrorResponseDto> handleException(MethodArgumentNotValidException exception) {
        List<String> errors = new ArrayList<>();
        for (ObjectError objectError : exception.getAllErrors()) {
            errors.add(objectError.getDefaultMessage());
        }
        ListOfErrorResponseDto response = new ListOfErrorResponseDto(new Timestamp(System.currentTimeMillis()), errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
