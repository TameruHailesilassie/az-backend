package com.softech.ehr.exception;

public class EntityNotFoundException extends
    javax.persistence.EntityNotFoundException {


    private static final long serialVersionUID = -3284993425429534536L;

    public EntityNotFoundException(String name) {
        super(String.format("No Domain Entity found with name '%s'.", name));
    }
}
