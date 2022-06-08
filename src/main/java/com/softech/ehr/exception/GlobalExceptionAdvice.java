package com.softech.ehr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<ApiErrorMessage> resourceNotFoundException(
        NoUserFoundException ex, WebRequest request) {
        ApiErrorMessage message = ApiErrorMessage.builder()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .timestamp(new Date())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }





    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorMessage> entityNotFoundException(
        EntityNotFoundException ex, WebRequest request) {
        ApiErrorMessage message = ApiErrorMessage.builder()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .timestamp(new Date())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiErrorMessage> userAlreadyExistException(
        UserAlreadyExistException ex, WebRequest request) {
        ApiErrorMessage message = ApiErrorMessage.builder()
            .statusCode(HttpStatus.CONFLICT.value())
            .timestamp(new Date())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
