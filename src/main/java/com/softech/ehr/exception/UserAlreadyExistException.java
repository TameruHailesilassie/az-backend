package com.softech.ehr.exception;

public class UserAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = -3284993425429534536L;

    public UserAlreadyExistException(String phoneNumber) {
        super(String.format("user already Exist with  phone number %s",
            phoneNumber));
    }
}
