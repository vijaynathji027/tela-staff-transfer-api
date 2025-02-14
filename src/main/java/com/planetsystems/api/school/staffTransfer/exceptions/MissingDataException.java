package com.planetsystems.api.school.staffTransfer.exceptions;

public class MissingDataException extends RuntimeException{
    public MissingDataException(String message) {
        super(message);
    }
}
