package com.softech.ehr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class AzExceptionAdvice {

    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<ApiErrorMessage> resourceNotFoundException(
        NoUserFoundException ex, WebRequest request) {
        ApiErrorMessage message = ApiErrorMessage.builder()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .timestamp(LocalDateTime.now())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorMessage> badCredentialsException(
        BadCredentialsException ex, WebRequest request) {
        ApiErrorMessage message = ApiErrorMessage.builder()
            .statusCode(HttpStatus.UNAUTHORIZED.value())
            .timestamp(LocalDateTime.now())
            .message(ex.getMessage())
            .description("Username or Password Error!")
            .build();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorMessage> accessDeniedException(
        AccessDeniedException ex, WebRequest request) {
        ApiErrorMessage message = ApiErrorMessage.builder()
            .statusCode(HttpStatus.FORBIDDEN.value())
            .timestamp(LocalDateTime.now())
            .message(ex.getMessage())
            .description("You are not allowed to access this data!")
            .build();
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorMessage> entityNotFoundException(
        EntityNotFoundException ex, WebRequest request) {
        ApiErrorMessage message = ApiErrorMessage.builder()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .timestamp(LocalDateTime.now())
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
            .timestamp(LocalDateTime.now())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
