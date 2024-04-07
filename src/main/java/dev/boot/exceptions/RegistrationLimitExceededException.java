package dev.boot.exceptions;

public class RegistrationLimitExceededException extends RuntimeException {
    public RegistrationLimitExceededException(String message) {
        super(message);
    }
}
