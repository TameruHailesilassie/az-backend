package com.softech.ehr.exception;

public class TokenRefreshException extends RuntimeException {

    private static final long serialVersionUID = -3284993425429534536L;

    public TokenRefreshException(String message) {
        super(message);
    }
}