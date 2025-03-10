package com.planetsystems.api.school.staffTransfer.exceptions;

public class MultipleResultsFoundException extends RuntimeException {
    public MultipleResultsFoundException(String message) {
        super(message);
    }
}
