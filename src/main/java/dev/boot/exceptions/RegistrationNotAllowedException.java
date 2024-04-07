package dev.boot.exceptions;

public class RegistrationNotAllowedException extends RuntimeException{
    public RegistrationNotAllowedException(String message) {
        super(message);
    }
}
