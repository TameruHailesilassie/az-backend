package com.softech.ehr.exception.validators;

import com.softech.ehr.exception.ApiErrorMessage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ConstraintViolationAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorMessage> resourceNotFoundException(
        ConstraintViolationException ex, WebRequest request) {
        ValidationErrorMessage error = new ValidationErrorMessage();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            error.getViolations().add(
                new Violation(violation.getPropertyPath().toString(),
                    violation.getMessage()));
        }
        ApiErrorMessage message = ApiErrorMessage.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .timestamp(LocalDateTime.now())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .causes(error.getViolations())
            .build();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiErrorMessage> onMethodArgumentNotValidException(
        MethodArgumentNotValidException ex, WebRequest request) {
        ValidationErrorMessage error = new ValidationErrorMessage();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.getViolations().add(new Violation(fieldError.getField(),
                fieldError.getDefaultMessage()));
        }
        ApiErrorMessage message = ApiErrorMessage.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .timestamp(LocalDateTime.now())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .causes(error.getViolations())
            .build();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
