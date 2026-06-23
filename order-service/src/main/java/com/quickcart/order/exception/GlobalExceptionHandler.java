package com.quickcart.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleResourceNotFound(
            ResourceNotFoundException ex) {

        ErrorResponse response =
                new ErrorResponse();

        response.setTimestamp(
                LocalDateTime.now());

        response.setStatus(404);

        response.setError(
                ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(
            BadRequestException.class)
    public ResponseEntity<ErrorResponse>
    handleBadRequest(
            BadRequestException ex) {

        ErrorResponse response =
                new ErrorResponse();

        response.setTimestamp(
                LocalDateTime.now());

        response.setStatus(400);

        response.setError(
                ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>
    handleValidation(
            MethodArgumentNotValidException ex) {

        ErrorResponse response =
                new ErrorResponse();

        response.setTimestamp(
                LocalDateTime.now());

        response.setStatus(400);

        response.setError(
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage());

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}