package com.invoiceprocessing.server.exception;

/**
 * Custom Exception für Authentifizierungsfehler
 */
public class AuthenticationException extends RuntimeException {
    private final int statusCode;
    private final String errorCode;

    public AuthenticationException(String message, int statusCode, String errorCode) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    public AuthenticationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = "AUTHENTICATION_ERROR";
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
