package com.invoiceprocessing.server.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global Exception Handler für alle Custom Exceptions
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Behandelt AuthenticationException
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", ex.getStatusCode());
        response.put("error", ex.getErrorCode());
        response.put("message", ex.getMessage());

        HttpStatus httpStatus = HttpStatus.resolve(ex.getStatusCode());
        if (httpStatus == null) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Allgemeiner Exception Handler für unerwartete Fehler
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 500);
        response.put("error", "INTERNAL_SERVER_ERROR");
        response.put("message", "Ein unerwarteter Fehler ist aufgetreten");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
