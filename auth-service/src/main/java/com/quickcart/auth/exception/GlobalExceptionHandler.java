package com.quickcart.auth.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse response =
                buildErrorResponse(
                        HttpStatus.NOT_FOUND,
                        ErrorCode.COMMON_404,
                        ex.getMessage(),
                        request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request) {

        ErrorResponse response =
                buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        ErrorCode.COMMON_400,
                        ex.getMessage(),
                        request.getRequestURI());

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            UnauthorizedException ex,
            HttpServletRequest request) {

        ErrorResponse response =
                buildErrorResponse(
                        HttpStatus.UNAUTHORIZED,
                        ErrorCode.COMMON_401,
                        ex.getMessage(),
                        request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            ConflictException ex,
            HttpServletRequest request) {

        ErrorResponse response =
                buildErrorResponse(
                        HttpStatus.CONFLICT,
                        ErrorCode.COMMON_409,
                        ex.getMessage(),
                        request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> validationErrors =
                new HashMap<>();

        for (FieldError error :
                ex.getBindingResult().getFieldErrors()) {

            validationErrors.put(
                    error.getField(),
                    error.getDefaultMessage());
        }

        ErrorResponse response =
                buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        ErrorCode.COMMON_400,
                        "Validation Failed",
                        request.getRequestURI());

        response.setValidationErrors(
                validationErrors);

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        ErrorResponse response =
                buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        ErrorCode.COMMON_400,
                        ex.getMessage(),
                        request.getRequestURI());

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        ErrorResponse response =
                buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        ErrorCode.COMMON_400,
                        ex.getMessage(),
                        request.getRequestURI());

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ex.printStackTrace();

        ErrorResponse response =
                buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ErrorCode.COMMON_500,
                        "Something went wrong. Please try again later.",
                        request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    private ErrorResponse buildErrorResponse(
            HttpStatus status,
            ErrorCode errorCode,
            String message,
            String path) {

        ErrorResponse response =
                new ErrorResponse();

        response.setTimestamp(
                LocalDateTime.now());

        response.setStatus(
                status.value());

        response.setError(
                status.getReasonPhrase());

        response.setMessage(
                message);

        response.setPath(
                path);

        response.setCode(
                errorCode);

        return response;
    }
}